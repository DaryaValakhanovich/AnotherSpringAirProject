package com.air.company.spring.prototypes;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;

import java.time.LocalDateTime;

public class FlightPrototype {
    public static Flight testFlight() {
        Flight flight = new Flight();
        flight.setDeparture(LocalDateTime.parse("2000-01-01T10:00"));
        flight.setArrival(LocalDateTime.parse("2000-01-01T12:00"));
        flight.setStartAirport("testFirstAirport");
        flight.setFinalAirport("testFinalAirport");
        return flight;
    }

    public static Flight testFlight2() {
        Flight flight = new Flight();
        flight.setDeparture(LocalDateTime.parse("2000-01-01T10:00"));
        flight.setArrival(LocalDateTime.parse("2000-01-01T12:00"));
        flight.setStartAirport("testFirstAirport");
        flight.setFinalAirport("ThirdTestAirport");
        return flight;
    }

    public static FlightsDto testFlightDto() {
        return FlightsDto.builder()
                .departure("2000-01-01T10:00")
                .arrival("2000-01-01T12:00")
                .startAirport("testFirstAirport")
                .finalAirport("testSecondAirport")
                .numberOfFreeSeats(PlanePrototype.testPlane().getNumberOfSeats())
                .build();
    }

    public static FlightsDto testFlightDto2() {
        return FlightsDto.builder()
                .departure("2000-01-01T10:00")
                .arrival("2000-01-01T12:00")
                .startAirport("ThirdTestAirport")
                .finalAirport("testSecondAirport")
                .numberOfFreeSeats(PlanePrototype.testPlane().getNumberOfSeats())
                .build();
    }

    public static FlightsDto testFlightDto3() {
        return FlightsDto.builder()
                .departure("2000-01-01T13:00")
                .arrival("2000-01-01T14:00")
                .startAirport("testSecondAirport")
                .finalAirport("ThirdTestAirport")
                .numberOfFreeSeats(PlanePrototype.testPlane().getNumberOfSeats())
                .build();
    }
}
