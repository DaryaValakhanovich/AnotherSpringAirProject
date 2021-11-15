package com.air.company.spring.repository;

import com.air.company.spring.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketsRepository extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {
}
