package com.air.company.spring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
@Data
@NoArgsConstructor
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "weight")
    private double weight;
    @Column(name = "cruising_speed")
    private double cruisingSpeed;
    @Column(name = "model", length = 250)
    private String model;
    @Column(name = "company", length = 250)
    private String company;
    @Column(name = "max_flight_altitude")
    private double maxFlightAltitude;
    @Column(name = "max_range_of_flight")
    private double maxRangeOfFlight;

}
