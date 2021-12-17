package com.air.company.spring.controller;

import com.air.company.spring.dto.assemblers.TicketResourceAssembler;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Account;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.dto.mappers.AccountsMapper;
import com.air.company.spring.dto.mappers.FlightsMapper;
import com.air.company.spring.dto.mappers.TicketsMapper;
import com.air.company.spring.service.AccountsService;
import com.air.company.spring.service.FlightsService;
import com.air.company.spring.service.TicketsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Log
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketsService ticketsService;
    @Autowired
    private AccountsService accountsService;
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private AccountsMapper accountsConverter;
    @Autowired
    private FlightsMapper flightsConverter;
    @Autowired
    private TicketsMapper ticketsConverter;
    @Autowired
    private TicketResourceAssembler assembler;

    @PostMapping("/deactivate/{ticketId}")
    public ResponseEntity<?> deactivate(@PathVariable Integer ticketId) {
        log.info("Handling deactivate ticket request: " + ticketId);
        ticketsService.deactivate(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/showMyTickets/")
    public ResponseEntity<PagedResources<TicketsDto>> findAccountTickets(@RequestBody TicketsDto ticketsDto,
                                                                         @PageableDefault PagedResourcesAssembler pagedAssembler,
                                                                         @RequestParam Integer page, Integer size, String sortField, String email) {
        log.info("Handling find account tickets request");
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        Page<TicketsDto> ticketPage = new PageImpl<>(ticketsService.findBy(ticketsDto),
                PageRequest.of(page, size, Sort.Direction.ASC, sortField), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(ticketPage, assembler), HttpStatus.OK);
    }


    @PostMapping("/createTicket")
    public ResponseEntity<Resource<TicketsDto>> createTicket(@RequestParam Integer flightId, Integer numberOfSeats, String email) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setFlight(flightsConverter.fromFlightDtoToFlight(flightsService.findById(flightId)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        ticketsDto = ticketsService.saveTicket(ticketsDto);
        log.info("Handling create ticket request: " + ticketsDto);
        return new ResponseEntity<>(new Resource(ticketsDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(TicketController.class, "findTicketById", Integer.class)),
                        ticketsDto.getId()).withSelfRel()), HttpStatus.OK);
    }

    @PostMapping("/createTransferTicket")
    public ResponseEntity<PagedResources<TicketsDto>> createTransferTicket(@RequestParam Integer numberOfSeats, String email,
                                                                           Integer flightListIndex, String departure, String startAirport,
                                                                           String finalAirport, Integer page, Integer size,
                                                                           @PageableDefault PagedResourcesAssembler pagedAssembler) throws ValidationException {
        TicketsDto ticketsDto;
        LocalDate date = LocalDate.parse(departure);

        List<List<Flight>> lists = flightsService.findRightDifficultWay(date, numberOfSeats, startAirport, finalAirport);
        List<Flight> newFlights = lists.get(flightListIndex);
        List<TicketsDto> resultTickets = new ArrayList<>();
        for (Flight flight : newFlights) {
            ticketsDto = new TicketsDto();
            ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
            ticketsDto.setNumberOfSeats(numberOfSeats);
            ticketsDto.setActive(null);
            ticketsDto.setId(null);
            ticketsDto.setFlight(flight);
            ticketsDto = ticketsService.saveTicket(ticketsDto);
            log.info("Handling create ticket request: " + ticketsDto);
            resultTickets.add(ticketsDto);
        }
        Page<TicketsDto> ticketPage = new PageImpl<>(resultTickets, PageRequest.of(page, size), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(ticketPage, assembler), HttpStatus.OK);
    }


    @GetMapping("/findTicketById/{id}")
    public ResponseEntity<Resource<TicketsDto>> findTicketById(@PathVariable Integer id) {
        log.info("Handling find ticket request");
        TicketsDto ticketsDto = ticketsService.findById(id);
        return new ResponseEntity<>(new Resource(ticketsDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(TicketController.class, "findTicketById", Integer.class)),
                        id).withSelfRel()), HttpStatus.OK);
    }

}
