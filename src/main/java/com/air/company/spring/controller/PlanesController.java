package com.air.company.spring.controller;

import com.air.company.spring.asamblers.PlaneResourceAssembler;
import com.air.company.spring.dto.PlanesDto;
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


import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RequestMapping("/planes")
@Log
@RestController
public class PlanesController {
    @Autowired
    private PlanesService planesService;
    @Autowired
    private PlaneResourceAssembler assembler;

    @GetMapping("/findAll")
    public ResponseEntity<PagedResources<PlanesDto>> findAllPlanes(@PageableDefault PagedResourcesAssembler pagedAssembler) {
        log.info("Handling find all planes request");
        List<PlanesDto> planesDtos = planesService.findAll();
        Page<PlanesDto> page = new PageImpl<>(planesDtos, PageRequest.of(0, 5), 1);
        return new ResponseEntity<>(pagedAssembler.toResource(page, assembler), HttpStatus.OK);
    }

    @GetMapping("/showPlane/{planeId}")
    public ResponseEntity<Resource<PlanesDto>> findPlaneById(@PathVariable Integer planeId) {
        log.info("Handling find ticket seats request");
        PlanesDto planesDto = planesService.findById(planeId);
        return new ResponseEntity<>(new Resource(planesDto,
                linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(PlanesController.class, "findPlaneById", Integer.class)),
                        planeId).withSelfRel()), HttpStatus.OK);
    }

    @PostMapping("/createPlane")
    public ResponseEntity<Resource<PlanesDto>> save(@RequestBody PlanesDto plane) {
        try {
            plane = planesService.savePlane(plane);
            log.info("Handling save planes: " + plane);
            return new ResponseEntity<>(new Resource(plane,
                   linkTo(Objects.requireNonNull(ReflectionUtils.findMethod(PlanesController.class, "findPlaneById", Integer.class)),
                            plane.getId()).withSelfRel()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
