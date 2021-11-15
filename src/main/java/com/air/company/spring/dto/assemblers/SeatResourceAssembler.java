package com.air.company.spring.dto.assemblers;

import com.air.company.spring.controller.PlanesController;
import com.air.company.spring.entity.Seat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class SeatResourceAssembler extends ResourceAssemblerSupport<Seat, BeanResource> {

    public SeatResourceAssembler() {
        super(Seat.class, BeanResource.class);
    }

    @Override
    public BeanResource toResource(Seat seat) {
        BeanResource resource = new BeanResource(seat);
        Link selfLink = linkTo(
                Objects.requireNonNull(ReflectionUtils.findMethod(PlanesController.class, "findPlaneById", Integer.class)),
                seat.getId()).withSelfRel()
                .withSelfRel();
        resource.add(selfLink);
        return resource;
    }

}