package com.air.company.spring.repository;

import com.air.company.spring.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightsRepository extends JpaRepository<Flight, Integer>, JpaSpecificationExecutor<Flight> {
    @Query("FROM Flight WHERE numberOfFreeSeats >= :number and departure between :departureStart and :departureFinal")
   List<Flight> findFlightsForDifficultWay(@Param("number")int numberOfSeats,
    @Param("departureStart")LocalDateTime departureStart, @Param("departureFinal")LocalDateTime departureFinal);
}
