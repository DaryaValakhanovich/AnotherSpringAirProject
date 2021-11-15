package com.air.company.spring.service;

import com.air.company.spring.dto.PlanesDto;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.prototypes.PlanePrototype;
import com.air.company.spring.service.impls.PlanesServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest()
@RunWith(SpringRunner.class)
public class PlaneServiceTest {
    @Autowired
    PlanesServiceImpl planesService;

    @Test
    public void savePlaneTest(){
        PlanesDto savedPlane = planesService.savePlane(PlanePrototype.testPlaneDto());
        PlanesDto foundPlane = planesService.findById(savedPlane.getId());
        Assertions.assertEquals(savedPlane, foundPlane);
        planesService.delete(savedPlane);
    }

    @Test
    public void findAllTest(){
        PlanesDto savedPlane = planesService.savePlane(PlanePrototype.testPlaneDto());
        Assertions.assertNotNull(planesService.findAll());
        Assertions.assertFalse(planesService.findAll().isEmpty());
        planesService.delete(savedPlane);
    }

}
