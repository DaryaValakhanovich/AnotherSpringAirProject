package com.air.company.spring.controller;

import com.air.company.spring.asamblers.SeatResourceAssembler;
import com.air.company.spring.dto.SeatsDto;
import com.air.company.spring.entity.Seat;
import com.air.company.spring.service.interfaces.SeatsService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Log
@RestController
@RequestMapping("/seats")
public class SeatsController {
    @Autowired
    private SeatsService seatsService;
    @Autowired
    private SeatResourceAssembler assembler;

    @GetMapping("/showSeats/{ticketId}")
    public ResponseEntity<PagedResources<Seat>> findAccountTickets(@PathVariable Integer ticketId, @PageableDefault PagedResourcesAssembler pagedAssembler) {
        log.info("Handling find ticket seats request");
        Page<Seat> page = new PageImpl<>(seatsService.findByTicketId(ticketId), PageRequest.of(0, 5), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
    }

    @GetMapping("/findSeatById/{id}")
    public ResponseEntity<Resource<SeatsDto>> findSeatById(@PathVariable Integer id) {
        log.info("Handling find seats request");
        SeatsDto seatsDto = seatsService.findById(id);
        return new ResponseEntity<>(new Resource(seatsDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(SeatsController.class, "findPlaneById", Integer.class)),
                        id).withSelfRel()), HttpStatus.OK);
    }
}
