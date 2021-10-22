package com.air.company.spring.service.defalt;

import com.air.company.spring.entity.Seat;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.repository.SeatsRepository;
import com.air.company.spring.service.interfaces.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultSeatsService implements SeatsService {
    private final SeatsRepository seatsRepository;

    @Override
    public void saveSeats(Ticket ticket, int numberOfFirstSeat, int amountOfSeats) {
        Seat seat;
        for (int i = 0; i < amountOfSeats; i++) {
            seat = new Seat();
            seat.setTicket(ticket);
            seat.setNumberOfSeat(numberOfFirstSeat);
            seatsRepository.save(seat);
            System.out.println(seat);
            numberOfFirstSeat--;
        }
    }

    @Override
    public List<Seat> findByTicketId(Integer ticketId) {
        return seatsRepository.findAllByTicketId(ticketId);
    }
}
