package com.air.company.spring.controller;

import com.air.company.spring.asamblers.TicketResourceAssembler;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.AccountsConverter;
import com.air.company.spring.service.convertors.FlightsConverter;
import com.air.company.spring.service.convertors.TicketsConverter;
import com.air.company.spring.service.interfaces.AccountsService;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.TicketsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private AccountsConverter accountsConverter;
    @Autowired
    private FlightsConverter flightsConverter;
    @Autowired
    private TicketsConverter ticketsConverter;
    @Autowired
    private TicketResourceAssembler assembler;

    @PostMapping("/deactivate/{ticketId}")
    public ResponseEntity<?> deactivate(@PathVariable Integer ticketId) {
        log.info("Handling deactivate ticket request: " + ticketId);
        ticketsService.deactivate(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/showMyTickets/{email}")
    public ResponseEntity<PagedResources<Ticket>> findAccountTickets(@PathVariable String email, @PageableDefault PagedResourcesAssembler pagedAssembler) {
        log.info("Handling find account tickets request");
        Page<Ticket> page = new PageImpl<>(ticketsService.findByAccountEmail(email),
                PageRequest.of(0, 5), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
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
                                                                 String finalAirport, @PageableDefault PagedResourcesAssembler pagedAssembler) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        LocalDate date = LocalDate.parse(departure);

        List<List<Flight>> lists = flightsService.findRightDifficultWay(date, numberOfSeats,
                startAirport, finalAirport);
        List<Flight> newFlights = lists.get(flightListIndex);
        List<Ticket> resultTickets = new ArrayList<>();
        for (Flight flight : newFlights) {
            ticketsDto.setFlight(flight);
            ticketsDto = ticketsService.saveTicket(ticketsDto);
            log.info("Handling create ticket request: " + ticketsDto);
            resultTickets.add(ticketsConverter.fromTicketDtoToTicket(ticketsDto));
            ticketsDto.setActive(null);
        }
        Page<Ticket> page = new PageImpl<>(resultTickets, PageRequest.of(0, 5), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
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
