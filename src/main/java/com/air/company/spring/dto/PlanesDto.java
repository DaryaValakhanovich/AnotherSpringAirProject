package com.air.company.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanesDto {

    private Integer id;
    private int numberOfSeats;
    private double weight;
    private double cruisingSpeed;
    private String model;
    private String company;
    private double maxFlightAltitude;
    private double maxRangeOfFlight;
}
