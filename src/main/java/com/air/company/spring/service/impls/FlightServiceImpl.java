package com.air.company.spring.service.impls;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.FlightsRepository;
import com.air.company.spring.dto.mappers.FlightsMapper;
import com.air.company.spring.service.FlightsService;
import com.air.company.spring.service.search.GenericSpecificationsBuilder;
import com.air.company.spring.service.search.SpecificationFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightsService {

    @Autowired
    private SpecificationFactory<Flight> flightSpecificationFactory;
    private final FlightsRepository flightsRepository;
    private final FlightsMapper flightsConverter;

    @Override
    public FlightsDto findById(Integer id) {
        Optional<Flight> flight = flightsRepository.findById(id);
        return flight.map(flightsConverter::fromFlightToFlightDto).orElse(null);
    }

    @Override
    public FlightsDto saveFlight(FlightsDto flightsDto) throws ValidationException {
        validateDates(flightsDto);
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

    private void validateDates(FlightsDto flightsDto) throws ValidationException {
        LocalDateTime departure = LocalDateTime.parse(flightsDto.getDeparture());
        LocalDateTime arrival =LocalDateTime.parse(flightsDto.getArrival());
        if (arrival.isBefore(departure) || arrival.isEqual(departure)) {
            throw new ValidationException("Enter correct dates. ");
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
        GenericSpecificationsBuilder<Flight> builder = new GenericSpecificationsBuilder<>();
        if (Objects.nonNull(departure)) {
            builder.with(flightSpecificationFactory.isBetween("departure", Arrays.asList(departure.atStartOfDay(), departure.plusDays(1).atStartOfDay())));
        }
        if (Objects.nonNull(startAirport)) {
            builder.with(flightSpecificationFactory.isEqual("startAirport", startAirport));
        }
        if (Objects.nonNull(finalAirport)) {
            builder.with(flightSpecificationFactory.isEqual("finalAirport", finalAirport));
        }
        if (numberOfSeats>0) {
            builder.with(flightSpecificationFactory.isGreaterThan("numberOfFreeSeats", numberOfSeats));
        }
        List<Flight>  newFlights = flightsRepository.findAll(builder.build());
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
