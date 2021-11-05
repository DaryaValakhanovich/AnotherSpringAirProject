package com.air.company.spring.controller;

import com.air.company.spring.asamblers.FlightResourceAssembler;
import com.air.company.spring.asamblers.PlaneResourceAssembler;
import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.dto.SeatsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RequestMapping("/flights")
@Log
@RestController
public class FlightsController {
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private PlanesService planesService;
    @Autowired
    private PlanesConverter planesConverter;
    @Autowired
    private FlightResourceAssembler assembler;


    @PostMapping("/findFlight")
    public ResponseEntity<PagedResources<List<Flight>>> findRightFlights(@RequestBody FlightsDto flightsDto,@PageableDefault PagedResourcesAssembler pagedAssembler) {
        try {
            List<List<Flight>> lists;
            log.info("Handling find right flights request");
            LocalDate date = LocalDate.parse(flightsDto.getDeparture());
            List<Flight> flights = flightsService.findRightFlights(date, flightsDto.getNumberOfFreeSeats(),
                    flightsDto.getStartAirport(), flightsDto.getFinalAirport());
            if (flights.isEmpty()) {
                lists = flightsService.findRightDifficultWay(date, flightsDto.getNumberOfFreeSeats(),
                        flightsDto.getStartAirport(), flightsDto.getFinalAirport());
                if (lists.isEmpty()) {
                    throw new ValidationException("There are no flights for you");
                }
            } else {
                lists = new ArrayList<>();
                lists.add(flights);
            }
            Page<List<Flight>> page = new PageImpl<>(lists, PageRequest.of(0, 5), 1);
            return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/findAll")
    public ResponseEntity<PagedResources<PlanesDto>> findAllPlanes(@PageableDefault PagedResourcesAssembler pagedAssembler) {
        log.info("Handling find all planes request");
        List<PlanesDto> planesDtos = planesService.findAll();
        Page<PlanesDto> page = new PageImpl<>(planesDtos, PageRequest.of(0, 5), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
    }


    @PostMapping("/createFlight")
    public ResponseEntity<Resource<FlightsDto>> save(@RequestBody FlightsDto flightsDto,
                                                        @RequestParam(defaultValue = "") Integer planeId) {
        try {
            flightsDto.setPlanes(planesConverter.fromPlaneDtoToPlane(planesService.findById(planeId)));
            flightsDto = flightsService.saveFlight(flightsDto);
            log.info("Handling save flight: " + flightsDto);
            return new ResponseEntity<>(new Resource(flightsDto,
                    linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(FlightsController.class, "findFlightById", Integer.class)),
                            flightsDto.getId()).withSelfRel()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findFlightById/{id}")
    public ResponseEntity<Resource<FlightsDto>> findFlightById(@PathVariable Integer id) {
        log.info("Handling find flight request");
        FlightsDto flightsDto = flightsService.findById(id);
        return new ResponseEntity<>(new Resource(flightsDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(FlightsController.class, "findFlightById", Integer.class)),
                        id).withSelfRel()), HttpStatus.OK);
    }
}
