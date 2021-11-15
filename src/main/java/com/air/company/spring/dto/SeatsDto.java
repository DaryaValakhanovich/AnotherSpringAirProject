package com.air.company.spring.dto;

import com.air.company.spring.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatsDto {

    private Integer id;
    private Ticket ticket;
    @Positive
    private int numberOfSeat;

}
