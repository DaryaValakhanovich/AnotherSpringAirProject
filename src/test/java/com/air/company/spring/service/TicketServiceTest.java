package com.air.company.spring.service;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Seat;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.prototypes.TicketPrototype;
import com.air.company.spring.dto.mappers.AccountsMapper;
import com.air.company.spring.dto.mappers.FlightsMapper;
import com.air.company.spring.dto.mappers.PlanesMapper;
import com.air.company.spring.service.impls.*;
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
    TicketServiceImpl ticketService;
    @Autowired
    AccountsServiceImpl accountsService;
    @Autowired
    AccountsMapper accountsConverter;
    @Autowired
    FlightServiceImpl flightService;
    @Autowired
    FlightsMapper flightsConverter;
    @Autowired
    PlanesServiceImpl planesService;
    @Autowired
    PlanesMapper planesConverter;
    @Autowired
    SeatsServiceImpl seatsService;

    @Test
    public void saveDeactivateTicketTest() throws ValidationException {
        TicketsDto ticketsDto = TicketPrototype.testTicketDto();
        ticketsDto.setAccount(accountsConverter.fromAccountDtoToAccount(accountsService.findById(1)));
        Flight flight = flightsConverter.fromFlightDtoToFlight(flightService.findById(9));
        ticketsDto.setFlight(flight);
        ticketsDto = ticketService.saveTicket(ticketsDto);
        TicketsDto foundTicket = ticketService.findById(ticketsDto.getId());
        Assertions.assertNotNull(foundTicket);

        Assertions.assertEquals(flight.getNumberOfFreeSeats() - ticketsDto.getNumberOfSeats(), flightService.findById(9).getNumberOfFreeSeats());
        Assertions.assertTrue(ticketService.findById(foundTicket.getId()).getActive());
        ticketService.deactivate(foundTicket.getId());
        Assertions.assertFalse(ticketService.findById(foundTicket.getId()).getActive());
        Assertions.assertEquals(flight.getNumberOfFreeSeats(), flightService.findById(9).getNumberOfFreeSeats());

        List<Seat> seatList = seatsService.findByTicketId(foundTicket.getId());
        Assertions.assertNotNull(seatList);
        for (Seat s : seatList) {
            seatsService.delete(s);
        }

        ticketService.delete(foundTicket);
    }

    @Test
    public void findByAccountEmail() {
        Assertions.assertNotNull(ticketService.findBy(TicketPrototype.testTicketDto()));
    }


}
