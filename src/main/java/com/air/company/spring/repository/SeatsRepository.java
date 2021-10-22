package com.air.company.spring.repository;

import com.air.company.spring.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByTicketId(Integer ticketId);
}
