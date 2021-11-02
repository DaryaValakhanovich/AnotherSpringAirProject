package com.air.company.spring.controller;

import com.air.company.spring.entity.Seat;
import com.air.company.spring.service.interfaces.SeatsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Log
@RestController
@RequestMapping("/seats")
public class SeatsController {
    @Autowired
    private SeatsService seatsService;

    @GetMapping("/showSeats/{ticketId}")
    public ResponseEntity<List<Seat>> findAccountTickets(@PathVariable Integer ticketId) {
        log.info("Handling find ticket seats request");
        return ResponseEntity.ok(seatsService.findByTicketId(ticketId));
    }
}
