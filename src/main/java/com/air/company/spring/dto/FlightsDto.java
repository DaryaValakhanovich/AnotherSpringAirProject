package com.air.company.spring.dto;

import com.air.company.spring.entity.Plane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public FlightsDto(String departure,  int numberOfFreeSeats, String startAirport, String finalAirport) {
        this.departure = departure;
        this.numberOfFreeSeats = numberOfFreeSeats;
        this.startAirport = startAirport;
        this.finalAirport = finalAirport;
    }
}
