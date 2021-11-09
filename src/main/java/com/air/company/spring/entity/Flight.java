package com.air.company.spring.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "departure", columnDefinition = "TIMESTAMP")
    private LocalDateTime departure;

    @Column(name = "arrival", columnDefinition = "TIMESTAMP")
    private LocalDateTime arrival;

    @Column(name = "number_of_free_seats")
    private int numberOfFreeSeats;

    @Column(name = "start_airport", length = 250)
    private String startAirport;

    @Column(name = "final_airport", length = 250)
    private String finalAirport;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private Plane plane;

    @Transient
    private String price;

}
