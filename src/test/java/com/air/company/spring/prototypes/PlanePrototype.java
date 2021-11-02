package com.air.company.spring.prototypes;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Plane;

public class PlanePrototype {
    public static Plane testPlane() {
        Plane p = new Plane();
        p.setCompany("testCompany");
        p.setCruisingSpeed(1.2);
        p.setModel("testModel");
        p.setMaxFlightAltitude(2.3);
        p.setMaxRangeOfFlight(3.4);
        p.setWeight(4.5);
        p.setNumberOfSeats(50);
        return p;
    }

    public static PlanesDto testPlaneDto() {
        return PlanesDto.builder()
                .company("testCompany")
                .cruisingSpeed(1.2)
                .model("testModel")
                .maxFlightAltitude(2.3)
                .maxRangeOfFlight(3.4)
                .weight(4.5)
                .numberOfSeats(50)
                .build();
    }
}
