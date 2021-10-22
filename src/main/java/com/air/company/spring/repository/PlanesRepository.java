package com.air.company.spring.repository;

import com.air.company.spring.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanesRepository  extends JpaRepository<Plane, Integer> {
}
