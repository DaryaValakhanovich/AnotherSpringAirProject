package com.air.company.spring.dto;

import com.air.company.spring.entity.Plane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightsDto {

    Integer id;
    String departure;
    String arrival;
    int numberOfFreeSeats;
    String startAirport;
    String finalAirport;
    Plane planes;
    String price;

}
