package com.air.company.spring.service;

import com.air.company.spring.entity.Ticket;
import com.air.company.spring.prototypes.TicketPrototype;
import com.air.company.spring.dto.mappers.TicketsMapper;
import com.air.company.spring.service.impls.SeatsServiceImpl;
import com.air.company.spring.service.impls.TicketServiceImpl;
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
    SeatsServiceImpl seatsService;
    @Autowired
    TicketServiceImpl ticketService;
    @Autowired
    TicketsMapper ticketsConverter;

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
