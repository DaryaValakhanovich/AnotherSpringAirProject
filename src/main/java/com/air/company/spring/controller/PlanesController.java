package com.air.company.spring.controller;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/planes")
@Log
@Controller
public class PlanesController {
    @Autowired
    private PlanesService planesService;

    @GetMapping("/findAll")
    public List<PlanesDto> findAllPlanes() {
        log.info("Handling find all planes request");
        return planesService.findAll();
    }

    @GetMapping("/showPlane/{planeId}")
    public String findAccountTickets(@PathVariable Integer planeId, Model model) {
        log.info("Handling find ticket seats request");
        model.addAttribute("plane", planesService.findById(planeId));
        return "showPlaneView";
    }

    @GetMapping("/createPlane")
    public String createPlane(Model model) {
        model.addAttribute("newPlane", new PlanesDto());
        return "createPlaneView";
    }

    @PostMapping("/createPlane")
    public String save(@ModelAttribute("newPlane") @Valid PlanesDto plane, Model model){
        try {
            log.info("Handling save planes: " + planesService.savePlane(plane));
        } catch (Exception exception){
            model.addAttribute("errorString", exception.getMessage());
            return "createPlaneView";
        }
        return "redirect:/";
    }
}
