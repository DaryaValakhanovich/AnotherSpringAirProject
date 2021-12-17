package com.air.company.spring.service;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.exception.ValidationException;

import java.awt.print.Pageable;
import java.util.List;

public interface PlanesService {

    PlanesDto savePlane(PlanesDto planesDto) throws ValidationException;

    List<PlanesDto> findAll(String sortField);

    PlanesDto findById(Integer id);

}
