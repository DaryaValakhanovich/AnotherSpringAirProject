package com.air.company.spring.dto;

import com.air.company.spring.entity.Account;
import com.air.company.spring.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketsDto {
    private Integer id;
    private Account account;
    private Flight flight;
    private int numberOfSeats;
    private Boolean active;
}
