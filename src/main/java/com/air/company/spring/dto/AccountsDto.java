package com.air.company.spring.dto;

import com.air.company.spring.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDto {
    private String email;
    private String password;
    private String number;
    private Integer id;
    private Set<Role> roles;
}
