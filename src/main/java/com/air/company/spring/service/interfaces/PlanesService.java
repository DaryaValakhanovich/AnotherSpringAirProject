package com.air.company.spring.service.interfaces;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.exception.ValidationException;

import java.util.List;

public interface PlanesService {
    PlanesDto savePlane(PlanesDto planesDto) throws ValidationException;

    List<PlanesDto> findAll();

    PlanesDto findById(Integer id);
}
