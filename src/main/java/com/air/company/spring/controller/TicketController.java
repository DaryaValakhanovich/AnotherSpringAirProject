package com.air.company.spring.controller;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.AccountsConverter;
import com.air.company.spring.service.convertors.FlightsConverter;
import com.air.company.spring.service.interfaces.AccountsService;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.TicketsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log
@Controller
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

    @PostMapping("/save")
    public TicketsDto saveTicket(@RequestBody TicketsDto ticketsDto) throws ValidationException {
        log.info("Handling save ticket: " + ticketsDto);
        return ticketsService.saveTicket(ticketsDto);
    }

    @GetMapping("/deactivate/{ticketId}")
    public String confirmDeactivate(@PathVariable Integer ticketId) {
        return "confirmDeactivate";
    }

    @PostMapping("/deactivate/{ticketId}")
    public String deactivate(@PathVariable Integer ticketId) {
        log.info("Handling deactivate ticket request: " + ticketId);
        ticketsService.deactivate(ticketId);
        return "redirect:/";
    }

    @GetMapping("/showMyTickets/{email}")
    public String findAccountTickets(@PathVariable String email, Model model) {
        log.info("Handling find account tickets request");
        model.addAttribute("allTickets", ticketsService.findByAccountEmail(email));
        return "showMyTickets";
    }


    @PostMapping("/createTicket")
    public String createTicket(@RequestParam Integer flightId, Integer numberOfSeats, String email) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setFlight(flightsConverter.fromFlightDtoToFlight(flightsService.findById(flightId)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        log.info("Handling create ticket request: " + ticketsService.saveTicket(ticketsDto));
        return "redirect:/";
    }

    @PostMapping("/createTransferTicket")
    public String createTransferTicket(@RequestParam Integer numberOfSeats, String email, FlightsDto flightsDto,
                                       Integer flightListIndex, String departure, String startAirport,
                                       String finalAirport) throws ValidationException {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findByEmail(email)));
        ticketsDto.setNumberOfSeats(numberOfSeats);
        LocalDate date = LocalDate.parse(departure);
        List<List<Flight>> lists = flightsService.findRightDifficultWay(date, numberOfSeats,
                startAirport, finalAirport);
        List<Flight> newFlights = lists.get(flightListIndex);
        for (Flight flight : newFlights) {
            ticketsDto.setFlight(flight);
            log.info("Handling create ticket request: " + ticketsService.saveTicket(ticketsDto));
            ticketsDto.setActive(null);
        }
        return "redirect:/";
    }
}
