package com.air.company.spring.service.convertors;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlightsConverter {
    public Flight fromFlightDtoToFlight(FlightsDto flightsDto) {
        Flight flight = new Flight();
        flight.setId(flightsDto.getId());
        flight.setDeparture(fromStringToLocalDateTime(flightsDto.getDeparture()));
        flight.setArrival(fromStringToLocalDateTime(flightsDto.getArrival()));
        flight.setNumberOfFreeSeats(flightsDto.getNumberOfFreeSeats());
        flight.setStartAirport(flightsDto.getStartAirport());
        flight.setFinalAirport(flightsDto.getFinalAirport());
        flight.setPlane(flightsDto.getPlanes());
        return flight;
    }

    public FlightsDto fromFlightToFlightDto(Flight flight) {
        return FlightsDto.builder()
                .id(flight.getId())
                .departure(flight.getDeparture().toString())
                .arrival(flight.getArrival().toString())
                .numberOfFreeSeats(flight.getNumberOfFreeSeats())
                .startAirport(flight.getStartAirport())
                .finalAirport(flight.getFinalAirport())
                .planes(flight.getPlane())
                .build();
    }

    private LocalDateTime fromStringToLocalDateTime(String s){
        return LocalDateTime.parse(s);
    }
}

