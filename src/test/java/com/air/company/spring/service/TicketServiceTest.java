package com.air.company.spring.service;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Seat;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.prototypes.FlightPrototype;
import com.air.company.spring.prototypes.PlanePrototype;
import com.air.company.spring.prototypes.TicketPrototype;
import com.air.company.spring.service.convertors.AccountsConverter;
import com.air.company.spring.service.convertors.FlightsConverter;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.convertors.TicketsConverter;
import com.air.company.spring.service.defalt.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class TicketServiceTest {

    @Autowired
    DefaultTicketService ticketService;
    @Autowired
    DefaultAccountsService accountsService;
    @Autowired
    AccountsConverter accountsConverter;
    @Autowired
    DefaultFlightService flightService;
    @Autowired
    FlightsConverter flightsConverter;
    @Autowired
    DefaultPlanesService planesService;
    @Autowired
    PlanesConverter planesConverter;
    @Autowired
    DefaultSeatsService seatsService;

    @Test
    public void saveDeactivateTicketTest() throws ValidationException {
        TicketsDto ticketsDto = TicketPrototype.testTicketDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findById(1)));
        Flight flight = flightsConverter.fromFlightDtoToFlight(flightService.findById(9));
        ticketsDto.setFlight(flight);
        ticketsDto = ticketService.saveTicket(ticketsDto);
        TicketsDto foundTicket = ticketService.findById(ticketsDto.getId());
        Assertions.assertNotNull(foundTicket);

        Assertions.assertEquals(flight.getNumberOfFreeSeats()-ticketsDto.getNumberOfSeats(), flightService.findById(9).getNumberOfFreeSeats());
        Assertions.assertTrue(ticketService.findById(foundTicket.getId()).getActive());
        ticketService.deactivate(foundTicket.getId());
        Assertions.assertFalse(ticketService.findById(foundTicket.getId()).getActive());
        Assertions.assertEquals(flight.getNumberOfFreeSeats(), flightService.findById(9).getNumberOfFreeSeats());

        List<Seat> seatList = seatsService.findByTicketId(foundTicket.getId());
        Assertions.assertNotNull(seatList);
        for (Seat s:seatList) {
            seatsService.delete(s);
        }

        ticketService.delete(foundTicket);
    }

    @Test
    public void findByAccountEmail() {
        Assertions.assertNotNull(ticketService.findByAccountEmail("admin@admin.ru"));
    }



}
