package com.air.company.spring.repository;

import com.air.company.spring.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByAccount_Email(String email);
}
