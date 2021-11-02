package com.air.company.spring.controller;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/planes")
@Log
@RestController
public class PlanesController {
    @Autowired
    private PlanesService planesService;

   @GetMapping("/findAll")
    public ResponseEntity<List<PlanesDto>> findAllPlanes() {
        log.info("Handling find all planes request");
        return ResponseEntity.ok(planesService.findAll());
    }

    @GetMapping("/showPlane/{planeId}")
    public ResponseEntity<PlanesDto> findAccountTickets(@PathVariable Integer planeId) {
        log.info("Handling find ticket seats request");
       // model.addAttribute("plane", planesService.findById(planeId));
        return ResponseEntity.ok(planesService.findById(planeId));
    }

    @PostMapping("/createPlane")
    public ResponseEntity<PlanesDto> save(@RequestBody PlanesDto plane){
        try {
            plane = planesService.savePlane(plane);
            log.info("Handling save planes: " + plane);
            return ResponseEntity.ok(plane);
        } catch (Exception exception){
           // model.addAttribute("errorString", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
