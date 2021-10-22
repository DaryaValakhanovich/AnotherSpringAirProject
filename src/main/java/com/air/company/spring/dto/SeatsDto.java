package com.air.company.spring.dto;

import com.air.company.spring.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatsDto {
    private Integer id;
    private Ticket ticket;
    private int numberOfSeat;
}
