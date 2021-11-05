package com.air.company.spring.service.interfaces;


import com.air.company.spring.dto.SeatsDto;
import com.air.company.spring.entity.Seat;
import com.air.company.spring.entity.Ticket;

import java.util.List;

public interface SeatsService {
    void saveSeats(Ticket ticket, int numberOfFirstSeat, int amountOfSeats);
   List<Seat> findByTicketId(Integer ticketId);
    SeatsDto findById(Integer id);
}
