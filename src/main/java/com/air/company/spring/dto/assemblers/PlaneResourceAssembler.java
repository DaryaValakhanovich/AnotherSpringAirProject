package com.air.company.spring.dto.assemblers;

import com.air.company.spring.controller.PlanesController;
import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Plane;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class PlaneResourceAssembler  extends ResourceAssemblerSupport<PlanesDto, BeanResource> {

    public PlaneResourceAssembler() {
        super(Plane.class, BeanResource.class);
    }

    @Override
    public BeanResource toResource(PlanesDto plane) {
        BeanResource planeResource = new BeanResource(plane);
        Link selfLink = linkTo(
                Objects.requireNonNull(ReflectionUtils.findMethod(PlanesController.class, "findPlaneById", Integer.class)),
                        plane.getId()).withSelfRel()
                .withSelfRel();
        planeResource.add(selfLink);
        return planeResource;
    }

}
