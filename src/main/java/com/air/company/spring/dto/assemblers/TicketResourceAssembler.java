package com.air.company.spring.dto.assemblers;

import com.air.company.spring.controller.TicketController;
import com.air.company.spring.dto.TicketsDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TicketResourceAssembler extends ResourceAssemblerSupport<TicketsDto, BeanResource> {

    public TicketResourceAssembler() {
        super(TicketsDto.class, BeanResource.class);
    }

    @Override
    public BeanResource toResource(TicketsDto ticketsDto) {
        BeanResource resource = new BeanResource(ticketsDto);
        Link selfLink = linkTo(
                Objects.requireNonNull(ReflectionUtils.findMethod(TicketController.class, "findTicketById", Integer.class)),
                ticketsDto.getId()).withSelfRel()
                .withSelfRel();
        resource.add(selfLink);
        return resource;
    }

}
