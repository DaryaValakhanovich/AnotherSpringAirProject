package com.air.company.spring.controller;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/findFlight")
    public ResponseEntity<List<List<Flight>>> findRightFlights(@RequestBody FlightsDto flightsDto) {
        try {
            List<List<Flight>> lists;
            log.info("Handling find right flights request");
            LocalDate date = LocalDate.parse(flightsDto.getDeparture());
            List<Flight> flights = flightsService.findRightFlights(date, flightsDto.getNumberOfFreeSeats(),
                    flightsDto.getStartAirport(), flightsDto.getFinalAirport());
            if (flights.isEmpty()) {
                lists = flightsService.findRightDifficultWay(date, flightsDto.getNumberOfFreeSeats(),
                        flightsDto.getStartAirport(), flightsDto.getFinalAirport());
                if(lists.isEmpty()){
                    throw new ValidationException("There are no flights for you");
                } else {
                    return ResponseEntity.ok(lists);
                }
            } else {
                lists = new ArrayList<>();
                lists.add(flights);
                return ResponseEntity.ok(lists);
            }
        } catch (Exception ex){
          //  model.addAttribute("errorString", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/createFlight")
    public ResponseEntity<FlightsDto> save(@RequestBody FlightsDto flightsDto,
                                         @RequestParam(defaultValue = "") Integer planeId){
        try {
            flightsDto.setPlanes(planesConverter.fromPlaneDtoToPlane(planesService.findById(planeId)));
            flightsDto = flightsService.saveFlight(flightsDto);
            log.info("Handling save flight: " + flightsDto);
            return ResponseEntity.ok(flightsDto);
        } catch (Exception exception){
        //    model.addAttribute("errorString", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
