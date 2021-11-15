package com.air.company.spring.dto.mappers;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Ticket;
import org.springframework.stereotype.Component;


@Component
public class TicketsMapper {
    public Ticket fromTicketDtoToTicket(TicketsDto ticketsDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketsDto.getId());
        ticket.setAccount(ticketsDto.getAccount());
        ticket.setFlight(ticketsDto.getFlight());
        ticket.setNumberOfSeats(ticketsDto.getNumberOfSeats());
        ticket.setActive(ticketsDto.getActive());
        return ticket;
    }

    public TicketsDto fromTicketToTicketDto(Ticket ticket) {
        return TicketsDto.builder()
                .id(ticket.getId())
                .account(ticket.getAccount())
                .flight(ticket.getFlight())
                .numberOfSeats(ticket.getNumberOfSeats())
                .active(ticket.getActive())
                .build();
    }
}
