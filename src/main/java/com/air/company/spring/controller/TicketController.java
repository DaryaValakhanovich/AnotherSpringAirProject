package com.air.company.spring.controller;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.AccountsConverter;
import com.air.company.spring.service.convertors.FlightsConverter;
import com.air.company.spring.service.interfaces.AccountsService;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.TicketsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/deactivate/{ticketId}")
    public ResponseEntity<?> deactivate(@PathVariable Integer ticketId) {
        log.info("Handling deactivate ticket request: " + ticketId);
        ticketsService.deactivate(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/showMyTickets/{email}")
    public ResponseEntity<List<Ticket>> findAccountTickets(@PathVariable String email) {
        log.info("Handling find account tickets request");
        return ResponseEntity.ok(ticketsService.findByAccountEmail(email));
    }


    @PostMapping("/createTicket")
    public ResponseEntity<TicketsDto> createTicket(@RequestParam Integer flightId, Integer numberOfSeats, String email) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setFlight(flightsConverter.fromFlightDtoToFlight(flightsService.findById(flightId)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        ticketsDto = ticketsService.saveTicket(ticketsDto);
        log.info("Handling create ticket request: " + ticketsDto);
        return ResponseEntity.ok(ticketsDto);
    }

    @PostMapping("/createTransferTicket")
    public ResponseEntity<List<TicketsDto>> createTransferTicket(@RequestParam Integer numberOfSeats, String email,
                                       Integer flightListIndex, String departure, String startAirport,
                                       String finalAirport) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        LocalDate date = LocalDate.parse(departure);
        List<List<Flight>> lists = flightsService.findRightDifficultWay(date, numberOfSeats,
                startAirport, finalAirport);
        List<Flight> newFlights = lists.get(flightListIndex);
        List<TicketsDto> resultTickets = new ArrayList<>();
        for (Flight flight : newFlights) {
            ticketsDto.setFlight(flight);
            ticketsDto = ticketsService.saveTicket(ticketsDto);
            log.info("Handling create ticket request: " + ticketsDto);
            resultTickets.add(ticketsDto);
            ticketsDto.setActive(null);
        }
        return ResponseEntity.ok(resultTickets);
    }
}
