package com.air.company.spring.service;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.prototypes.FlightPrototype;
import com.air.company.spring.prototypes.PlanePrototype;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.defalt.DefaultFlightService;
import com.air.company.spring.service.defalt.DefaultPlanesService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest()
@RunWith(SpringRunner.class)
public class FlightServiceTest {
    @Autowired
    private DefaultFlightService flightService;
    @Autowired
    private DefaultPlanesService planesService;
    @Autowired
    private PlanesConverter planesConverter;

    @Test
    public void saveFlight() throws ValidationException {
        FlightsDto flightsDto = FlightPrototype.testFlightDto();
        flightsDto.setPlanes(planesConverter.fromPlaneDtoToPlane(planesService.findById(1)));
        flightsDto = flightService.saveFlight(flightsDto);
        FlightsDto foundFlight = flightService.findById(flightsDto.getId());
        Assertions.assertNotNull(foundFlight);
        Assertions.assertEquals(foundFlight.getDeparture(), FlightPrototype.testFlightDto().getDeparture());
        Assertions.assertEquals(foundFlight.getArrival(), FlightPrototype.testFlightDto().getArrival());
        Assertions.assertEquals(foundFlight.getStartAirport(), FlightPrototype.testFlightDto().getStartAirport());
        Assertions.assertEquals(foundFlight.getFinalAirport(), FlightPrototype.testFlightDto().getFinalAirport());
        Assertions.assertEquals(foundFlight.getNumberOfFreeSeats(), PlanePrototype.testPlane().getNumberOfSeats());
        flightService.delete(foundFlight);
    }


    @Test
    public void findAllTest() {
        Assertions.assertNotNull(flightService.findAll());
        Assertions.assertFalse(flightService.findAll().isEmpty());
    }

    @Test
    public void getPriceTest() {
        Assertions.assertEquals(flightService.getPrice(FlightPrototype.testFlight(), 2), "144,00");
    }

    @Test
    public void findRightFlightsTest() throws ValidationException {
        PlanesDto planesDto = planesService.findById(1);
        FlightsDto flightsDto1 = FlightPrototype.testFlightDto();
        flightsDto1.setPlanes(planesConverter.fromPlaneDtoToPlane(planesDto));
        flightsDto1 = flightService.saveFlight(flightsDto1);

        FlightsDto flightsDto2 = FlightPrototype.testFlightDto();
        flightsDto2.setPlanes(planesConverter.fromPlaneDtoToPlane(planesDto));
        flightsDto2.setStartAirport("ThirdTestAirport");
        flightsDto2 = flightService.saveFlight(flightsDto2);

        List<Flight> list = flightService.findRightFlights
                (FlightPrototype.testFlight().getDeparture().toLocalDate(), 1,
                        FlightPrototype.testFlight().getStartAirport(), FlightPrototype.testFlight().getFinalAirport());

        Assertions.assertEquals(list.get(0).getId(), flightsDto1.getId());

        Assertions.assertNotNull(list);

        flightService.delete(flightsDto1);
        flightService.delete(flightsDto2);
    }

    @Test
    public void findRightDifficultWayTest() throws ValidationException {
        PlanesDto planesDto = planesService.findById(1);

        FlightsDto flightsDto1 = FlightPrototype.testFlightDto();
        flightsDto1.setPlanes(planesConverter.fromPlaneDtoToPlane(planesDto));
        flightsDto1 = flightService.saveFlight(flightsDto1);

        FlightsDto flightsDto2 = FlightPrototype.testFlightDto2();
        flightsDto2.setPlanes(planesConverter.fromPlaneDtoToPlane(planesDto));
        flightsDto2 = flightService.saveFlight(flightsDto2);

        FlightsDto flightsDto3 = FlightPrototype.testFlightDto3();
        flightsDto3.setPlanes(planesConverter.fromPlaneDtoToPlane(planesDto));
        flightsDto3 = flightService.saveFlight(flightsDto3);

        List<List<Flight>> list = flightService.findRightDifficultWay
                (FlightPrototype.testFlight2().getDeparture().toLocalDate(), 1,
                        FlightPrototype.testFlight2().getStartAirport(), FlightPrototype.testFlight2().getFinalAirport());

        Assertions.assertEquals(list.get(0).get(0).getId(), flightsDto1.getId());
        Assertions.assertEquals(list.get(0).get(1).getId(), flightsDto3.getId());

        Assertions.assertNotNull(list);
        flightService.delete(flightsDto1);
        flightService.delete(flightsDto2);
        flightService.delete(flightsDto3);
    }

    @Test
    public void buyReturnTicketTest() throws ValidationException {
        FlightsDto flightsDto = FlightPrototype.testFlightDto();
        flightsDto.setPlanes(planesConverter.fromPlaneDtoToPlane(planesService.findById(1)));
        flightsDto = flightService.saveFlight(flightsDto);
        FlightsDto foundFlight = flightService.findById(flightsDto.getId());

        flightService.buyTicket(foundFlight.getId(), 2);
        foundFlight = flightService.findById(flightsDto.getId());
        Assertions.assertEquals(foundFlight.getNumberOfFreeSeats(), PlanePrototype.testPlane().getNumberOfSeats()-2);

        flightService.returnTicket(flightsDto.getId(), 2);
        foundFlight = flightService.findById(flightsDto.getId());
        Assertions.assertEquals(foundFlight.getNumberOfFreeSeats(), PlanePrototype.testPlane().getNumberOfSeats());

        flightService.delete(foundFlight);
    }
}
