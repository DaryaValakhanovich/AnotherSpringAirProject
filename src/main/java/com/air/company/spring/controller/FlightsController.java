package com.air.company.spring.controller;

import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/flights")
@Log
@Controller
public class FlightsController {
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private PlanesService planesService;
    @Autowired
    private PlanesConverter planesConverter;

    @GetMapping("/findFlight")
    public String findFlights(Model model) {
        model.addAttribute("flight", new FlightsDto());
        return "findFlightView";
    }

    @PostMapping("/findFlight")
    public String findRightFlights(@ModelAttribute("newFlight") @Valid FlightsDto flightsDto, Model model) {
        try {
            log.info("Handling find right flights request");
            LocalDate date = LocalDate.parse(flightsDto.getDeparture());
            List<Flight> flights = flightsService.findRightFlights(date, flightsDto.getNumberOfFreeSeats(),
                    flightsDto.getStartAirport(), flightsDto.getFinalAirport());
            model.addAttribute("numberOfSeats", flightsDto.getNumberOfFreeSeats());
            if (flights.isEmpty()) {
                List<List<Flight>> lists = flightsService.findRightDifficultWay(date, flightsDto.getNumberOfFreeSeats(),
                        flightsDto.getStartAirport(), flightsDto.getFinalAirport());
                if(lists.isEmpty()){
                    throw new ValidationException("There are no flights for you");
                } else {
                    model.addAttribute("listsOfFlights", lists);
                    model.addAttribute("flightsDto", flightsDto);
                    return "chooseDifficultWayView";
                }
            } else {
                model.addAttribute("flights", flights);
                return "chooseFlightsView";
            }
        } catch (Exception ex){
            model.addAttribute("flight", new FlightsDto());
            model.addAttribute("errorString", ex.getMessage());
            return "findFlightView";
        }
    }

    @GetMapping("/findRightDifficultWay")
    public List<List<Flight>> findRightDifficultWay(@RequestBody LocalDate departure, int numberOfSeats,
                                                    String startAirport, String finalAirport) {
        log.info("Handling find right difficult way flights request");
        return flightsService.findRightDifficultWay(departure, numberOfSeats, startAirport, finalAirport);
    }

    @GetMapping("/createFlight")
    public String createFlight(Model model) {
        model.addAttribute("newFlight", new FlightsDto());
        model.addAttribute("allPlanes", planesService.findAll());
        return "createFlightView";
    }

    @PostMapping("/createFlight")
    public String save(@ModelAttribute("newFlight") @Valid FlightsDto flightsDto,
                       @RequestParam(defaultValue = "") Integer planeId,
                       Model model){
        try {
            flightsDto.setPlanes(planesConverter.fromPlaneDtoToPlane(planesService.findById(planeId)));
            log.info("Handling save flight: " + flightsService.saveFlight(flightsDto));
        } catch (Exception exception){
            model.addAttribute("errorString", exception.getMessage());
            return "createFlightView";
        }
        return "redirect:/";
    }
}
