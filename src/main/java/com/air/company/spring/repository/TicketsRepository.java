package com.air.company.spring.repository;

import com.air.company.spring.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TicketsRepository
extends JpaRepository<Ticket, Integer>
       // extends PagingAndSortingRepository<Ticket, Integer>
{
    List<Ticket> findAllByAccount_Email(String email);
}
