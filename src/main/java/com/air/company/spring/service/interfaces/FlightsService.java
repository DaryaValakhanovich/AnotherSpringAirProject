package com.air.company.spring.service.interfaces;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;

import java.time.LocalDate;
import java.util.List;

public interface FlightsService {

    FlightsDto findById(Integer id);

    List<FlightsDto> findAll();

    FlightsDto saveFlight(FlightsDto flightsDto) throws ValidationException;

    List<Flight> findRightFlights(LocalDate departure, int numberOfSeats,
                                  String startAirport, String finalAirport);

    List<List<Flight>>  findRightDifficultWay(LocalDate departure, int numberOfSeats,
                                       String startAirport, String finalAirport);

    String getPrice(Flight flight, int numberOfSeats);

    void buyTicket(Integer id, int numberOfSeats);

    void returnTicket(Integer id, int numberOfSeats);

    List<FlightsDto> findBy(FlightsDto flightsDto);
}
