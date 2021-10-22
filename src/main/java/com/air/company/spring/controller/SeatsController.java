package com.air.company.spring.controller;

import com.air.company.spring.service.interfaces.SeatsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Log
public class SeatsController {
    @Autowired
    private SeatsService seatsService;

    @GetMapping("/showSeats/{ticketId}")
    public String findAccountTickets(@PathVariable Integer ticketId, Model model) {
        log.info("Handling find ticket seats request");
        model.addAttribute("seats", seatsService.findByTicketId(ticketId));
        return "showSeatsView";
    }
}
