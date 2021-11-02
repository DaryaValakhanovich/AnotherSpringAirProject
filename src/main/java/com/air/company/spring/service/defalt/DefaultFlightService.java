package com.air.company.spring.service.defalt;


import com.air.company.spring.dto.AccountsDto;
import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.FlightsRepository;
import com.air.company.spring.service.convertors.FlightsConverter;
import com.air.company.spring.service.interfaces.FlightsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultFlightService implements FlightsService {
    private final FlightsRepository flightsRepository;
    private final FlightsConverter flightsConverter;

    @Override
    public FlightsDto findById(Integer id) {
        Optional<Flight> flight = flightsRepository.findById(id);
        return flight.map(flightsConverter::fromFlightToFlightDto).orElse(null);
    }

    @Override
    public FlightsDto saveFlight(FlightsDto flightsDto) throws ValidationException {
        validateFlightDto(flightsDto);
        if (flightsDto.getNumberOfFreeSeats() == 0) {
            flightsDto.setNumberOfFreeSeats(flightsDto.getPlanes().getNumberOfSeats());
        }
        Flight savedFlight = flightsRepository.save(flightsConverter.fromFlightDtoToFlight(flightsDto));
        return flightsConverter.fromFlightToFlightDto(savedFlight);
    }

    @Override
    public List<FlightsDto> findAll() {
        return flightsRepository.findAll()
                .stream()
                .map(flightsConverter::fromFlightToFlightDto)
                .collect(Collectors.toList());
    }

    @Override
    public String getPrice(Flight flight, int numberOfSeats) {
        return String.format("%.2f", (flight.getArrival().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - flight.getDeparture().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                * numberOfSeats * 0.00001);
    }

    private void validateFlightDto(FlightsDto flightsDto) throws ValidationException {
        if (isNull(flightsDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(flightsDto.getDeparture()) || flightsDto.getDeparture().isEmpty()) {
            throw new ValidationException("Departure is null");
        }
        if (isNull(flightsDto.getArrival()) || flightsDto.getArrival().isEmpty()) {
            throw new ValidationException("Departure is null");
        }
        LocalDateTime departure = LocalDateTime.parse(flightsDto.getDeparture());
        LocalDateTime arrival =LocalDateTime.parse(flightsDto.getArrival());
        if (arrival.isBefore(departure) || arrival.isEqual(departure)) {
            throw new ValidationException("Enter correct dates. ");
        }

        if (isNull(flightsDto.getStartAirport()) || flightsDto.getStartAirport().isEmpty()) {
            throw new ValidationException("Start airport is null");
        }
        if (isNull(flightsDto.getFinalAirport()) || flightsDto.getFinalAirport().isEmpty()) {
            throw new ValidationException("Final airport is null");
        }
        if (isNull(flightsDto.getPlanes()) ) {
            throw new ValidationException("Plane is null");
        }
    }


    @Override
    public List<List<Flight>> findRightDifficultWay(LocalDate departure, int numberOfSeats,
                                                    String startAirport, String finalAirport) {
        List<Flight> flights = findDifficultWay(startAirport, finalAirport,
                flightsRepository.findFlightsForDifficultWay
                        (numberOfSeats, departure.atStartOfDay(), departure.plusDays(2).atStartOfDay()), new ArrayList<>());

        List<List<Flight>> newFlights = new ArrayList<>();
        List<Flight> partOfNewFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight == null) {
                newFlights.add(partOfNewFlights);
                partOfNewFlights = new ArrayList<>();
            } else {
                partOfNewFlights.add(flight);
            }
        }

        for (List<Flight> listFlight : newFlights) {
            for (Flight flight : listFlight) {
                flight.setPrice(getPrice(flight, numberOfSeats));
            }
        }
        return newFlights;
    }


    @Override
    public List<Flight> findRightFlights(LocalDate departure, int numberOfSeats, String startAirport, String finalAirport) {

        List<Flight> newFlights = flightsRepository.findRightFlights(departure.atStartOfDay(), departure.plusDays(1).atStartOfDay(),
                numberOfSeats, startAirport, finalAirport);
        for (Flight flight : newFlights) {
            flight.setPrice(getPrice(flight, numberOfSeats));
        }
        return newFlights;
    }

    @Override
    public void buyTicket(Integer id, int numberOfSeats) {
        FlightsDto flightsDto = findById(id);
        flightsDto.setNumberOfFreeSeats(flightsDto.getNumberOfFreeSeats() - numberOfSeats);
        flightsRepository.save(flightsConverter.fromFlightDtoToFlight(flightsDto));
    }

    @Override
    public void returnTicket(Integer id, int numberOfSeats) {
        FlightsDto flightsDto = findById(id);
        flightsDto.setNumberOfFreeSeats(flightsDto.getNumberOfFreeSeats() + numberOfSeats);
        flightsRepository.save(flightsConverter.fromFlightDtoToFlight(flightsDto));
    }

    private List<Flight> findDifficultWay(String startAirport, String finalAirport,
                                          List<Flight> allFlights, List<Flight> previousFlights) {
        List<Flight> resultFlights = new ArrayList<>();
        List<Flight> probablyFlights = new ArrayList<>();
        for (Flight currentFlight : allFlights) {
            if (startAirport.equals(currentFlight.getStartAirport())) {
                if (previousFlights.isEmpty() ||
                        previousFlights.get(previousFlights.size() - 1).getArrival()
                                .isBefore(currentFlight.getDeparture())) {
                    if (currentFlight.getFinalAirport().equals(finalAirport)) {
                        resultFlights.addAll(previousFlights);
                        resultFlights.add(currentFlight);
                        resultFlights.add(null);
                    } else {
                        probablyFlights.add(currentFlight);
                    }
                }
            }
        }
        for (Flight probablyFlight : probablyFlights) {
            List<Flight> newPreviousFlights = new ArrayList<>(previousFlights);
            newPreviousFlights.add(probablyFlight);
            List<Flight> newFlights =
                    findDifficultWay(probablyFlight.getFinalAirport(), finalAirport, allFlights, newPreviousFlights);
            if (!newFlights.isEmpty()) {
                resultFlights.addAll(newFlights);
            }
        }
        return resultFlights;
    }
    public void delete(FlightsDto flightsDto) {
       flightsRepository.delete(flightsConverter.fromFlightDtoToFlight(flightsDto));
    }
}
