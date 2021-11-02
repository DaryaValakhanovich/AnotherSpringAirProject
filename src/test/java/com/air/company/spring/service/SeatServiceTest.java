package com.air.company.spring.service;

import com.air.company.spring.entity.Ticket;
import com.air.company.spring.prototypes.TicketPrototype;
import com.air.company.spring.service.convertors.TicketsConverter;
import com.air.company.spring.service.defalt.DefaultSeatsService;
import com.air.company.spring.service.defalt.DefaultTicketService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class SeatServiceTest {
    @Autowired
    DefaultSeatsService seatsService;
    @Autowired
    DefaultTicketService ticketService;
    @Autowired
    TicketsConverter ticketsConverter;

    @Test
    public void Test() {
        System.out.println(ticketService.findById(9));
        Ticket ticket = ticketsConverter.fromTicketDtoToTicket(ticketService.findById(9));
        System.out.println(ticket);
        seatsService.saveSeats(ticket,
                50, TicketPrototype.testTicket().getNumberOfSeats());
        Assertions.assertNotNull(seatsService.findByTicketId(9));
    }

}
