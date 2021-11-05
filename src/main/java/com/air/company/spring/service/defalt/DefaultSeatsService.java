package com.air.company.spring.service.defalt;


import com.air.company.spring.dto.SeatsDto;
import com.air.company.spring.entity.Plane;
import com.air.company.spring.entity.Seat;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.repository.SeatsRepository;
import com.air.company.spring.service.convertors.SeatsConverter;
import com.air.company.spring.service.interfaces.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultSeatsService implements SeatsService {
    private final SeatsRepository seatsRepository;
    private final SeatsConverter seatsConverter;

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

    @Override
    public SeatsDto findById(Integer id) {
        Optional<Seat> seat =seatsRepository.findById(id);
        return seat.map(seatsConverter::fromSeatToSeatDto).orElse(null);
    }

    public void delete(Seat seat) {
        seatsRepository.delete(seat);
    }


}
