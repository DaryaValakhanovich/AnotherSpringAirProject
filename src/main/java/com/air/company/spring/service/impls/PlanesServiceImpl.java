package com.air.company.spring.service.impls;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Plane;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.PlanesRepository;
import com.air.company.spring.dto.mappers.PlanesMapper;
import com.air.company.spring.service.PlanesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class PlanesServiceImpl implements PlanesService {

    private final PlanesRepository planesRepository;
    private final PlanesMapper planesConverter;

    @Override
    public PlanesDto savePlane(PlanesDto planesDto){
        Plane savedPlane = planesRepository.save(planesConverter.fromPlaneDtoToPlane(planesDto));
        return planesConverter.fromPlaneToPlaneDto(savedPlane);
    }

    @Override
    public List<PlanesDto> findAll(String sortField) {
        return planesRepository.findAll(Sort.by(sortField))
                .stream()
                .map(planesConverter::fromPlaneToPlaneDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlanesDto findById(Integer id) {
        Optional<Plane> planes = planesRepository.findById(id);
        return planes.map(planesConverter::fromPlaneToPlaneDto).orElse(null);
    }
    public void delete(PlanesDto planesDto) {
        planesRepository.delete(planesConverter.fromPlaneDtoToPlane(planesDto));
    }

}
