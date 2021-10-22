package com.air.company.spring.service.convertors;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Plane;
import org.springframework.stereotype.Component;

@Component
public class PlanesConverter {

    public Plane fromPlaneDtoToPlane(PlanesDto planesDto) {
        Plane planes = new Plane();
        planes.setId(planesDto.getId());
        planes.setCompany(planesDto.getCompany());
        planes.setCruisingSpeed(planesDto.getCruisingSpeed());
        planes.setMaxFlightAltitude(planesDto.getMaxFlightAltitude());
        planes.setMaxRangeOfFlight(planesDto.getMaxRangeOfFlight());
        planes.setModel(planesDto.getModel());
        planes.setWeight(planesDto.getWeight());
        planes.setNumberOfSeats(planesDto.getNumberOfSeats());
        return planes;
    }

    public PlanesDto fromPlaneToPlaneDto(Plane planes) {
        return PlanesDto.builder()
                .id(planes.getId())
                .company(planes.getCompany())
                .cruisingSpeed(planes.getCruisingSpeed())
                .maxFlightAltitude(planes.getMaxFlightAltitude())
                .maxRangeOfFlight(planes.getMaxRangeOfFlight())
                .model(planes.getModel())
                .numberOfSeats(planes.getNumberOfSeats())
                .weight(planes.getWeight())
                .build();
    }
}
