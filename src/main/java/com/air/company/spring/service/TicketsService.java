package com.air.company.spring.service;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;

import java.util.List;

public interface TicketsService {

    TicketsDto saveTicket(TicketsDto ticketsDto) throws ValidationException;

    void deactivate(Integer id);

    TicketsDto findById(Integer id);

    List<TicketsDto> findBy(TicketsDto ticketsDto);

}
