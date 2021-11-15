package com.air.company.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanesDto {

    private Integer id;
    @Positive
    private int numberOfSeats;
    @Positive
    private double weight;
    @Positive
    private double cruisingSpeed;
    private String model;
    private String company;
    @Positive
    private double maxFlightAltitude;
    @Positive
    private double maxRangeOfFlight;

}
