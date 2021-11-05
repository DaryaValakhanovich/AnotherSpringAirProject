package com.air.company.spring.asamblers;

import com.air.company.spring.controller.FlightsController;
import com.air.company.spring.dto.FlightsDto;
import com.air.company.spring.entity.Flight;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class FlightResourceAssembler extends ResourceAssemblerSupport<List<Flight>, BeanResource> {

    public FlightResourceAssembler() {
        super(FlightsDto.class, BeanResource.class);
    }

    @Override
    public BeanResource toResource(List<Flight> flights) {
        BeanResource resource = new BeanResource(flights);
        Link selfLink = linkTo(
                Objects.requireNonNull(ReflectionUtils.findMethod(FlightsController.class, "findFlightById", Integer.class)),
                flights.get(0).getId()).withSelfRel()
                .withSelfRel();
        resource.add(selfLink);
        return resource;
    }
}