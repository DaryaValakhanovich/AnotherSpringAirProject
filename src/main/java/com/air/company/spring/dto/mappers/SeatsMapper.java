package com.air.company.spring.dto.mappers;

import com.air.company.spring.dto.SeatsDto;
import com.air.company.spring.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatsMapper {

    public Seat fromSeatDtoToSeat(SeatsDto seatsDto) {
        Seat seat = new Seat();
        seat.setId(seatsDto.getId());
        seat.setTicket(seatsDto.getTicket());
        seat.setNumberOfSeat(seatsDto.getNumberOfSeat());
        return seat;
    }

    public SeatsDto fromSeatToSeatDto(Seat seat) {
        return SeatsDto.builder()
                .id(seat.getId())
                .ticket(seat.getTicket())
                .numberOfSeat(seat.getNumberOfSeat())
                .build();
    }
}