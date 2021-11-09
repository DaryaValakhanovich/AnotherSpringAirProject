package com.air.company.spring.service.imls;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.entity.Plane;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.PlanesRepository;
import com.air.company.spring.service.convertors.PlanesConverter;
import com.air.company.spring.service.interfaces.PlanesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ImplPlanesService implements PlanesService {

    private final PlanesRepository planesRepository;
    private final PlanesConverter planesConverter;

    @Override
    public PlanesDto savePlane(PlanesDto planesDto) throws ValidationException {
        validatePlaneDto(planesDto);
        Plane savedPlane = planesRepository.save(planesConverter.fromPlaneDtoToPlane(planesDto));
        return planesConverter.fromPlaneToPlaneDto(savedPlane);
    }

    private void validatePlaneDto(PlanesDto planesDto) throws ValidationException {
        if (isNull(planesDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(planesDto.getModel()) || planesDto.getModel().isEmpty()) {
            throw new ValidationException("Model is empty");
        }
        if (isNull(planesDto.getCompany()) || planesDto.getCompany().isEmpty()) {
            throw new ValidationException("Company is empty");
        }
        if (planesDto.getNumberOfSeats()<=0) {
            throw new ValidationException("Wrong number of seats");
        }
        if (planesDto.getWeight()<=0) {
            throw new ValidationException("Wrong weight");
        }
        if (planesDto.getCruisingSpeed()<=0) {
            throw new ValidationException("Wrong cruising speed");
        }
        if (planesDto.getMaxFlightAltitude()<=0) {
            throw new ValidationException("Wrong max flight altitude");
        }
        if (planesDto.getMaxRangeOfFlight()<=0) {
            throw new ValidationException("Wrong max range of flight");
        }
    }

    @Override
    public List<PlanesDto> findAll() {
        return planesRepository.findAll()
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
