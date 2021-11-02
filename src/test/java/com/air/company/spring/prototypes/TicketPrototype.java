package com.air.company.spring.prototypes;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Ticket;

public class TicketPrototype {
    public static Ticket testTicket() {
        Ticket a = new Ticket();
        a.setAccount(AccountPrototype.testAccount());
        a.setFlight(FlightPrototype.testFlight());
        a.setNumberOfSeats(2);
        return a;
    }

    public static TicketsDto testTicketDto() {
        return TicketsDto.builder()
                .account(AccountPrototype.testAccount())
                .flight(FlightPrototype.testFlight())
                .numberOfSeats(2)
                .build();
    }
}
