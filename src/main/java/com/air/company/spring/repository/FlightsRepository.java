package com.air.company.spring.repository;

import com.air.company.spring.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightsRepository extends JpaRepository<Flight, Integer> {
    @Query("FROM Flight WHERE departure between :departureStart and :departureFinal AND numberOfFreeSeats >= :number " +
            "AND startAirport = :start AND finalAirport = :final")
    List<Flight> findRightFlights
    (@Param("departureStart")LocalDateTime departureStart, @Param("departureFinal")LocalDateTime departureFinal,
     @Param("number") int numberOfSeats, @Param("start") String startAirport, @Param("final") String finalAirport);


    @Query("FROM Flight WHERE numberOfFreeSeats >= :number and departure between :departureStart and :departureFinal")
   List<Flight> findFlightsForDifficultWay(@Param("number")int numberOfSeats,
    @Param("departureStart")LocalDateTime departureStart, @Param("departureFinal")LocalDateTime departureFinal);
}
