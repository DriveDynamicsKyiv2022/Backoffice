package com.griddynamics.backoffice.service.area.impl;

import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.area.IAreaDao;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.coordinates.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimpleAreaServiceTest {
    private final IAreaDao areaDao = mock(IAreaDao.class);
    private SimpleAreaService areaService;

    @BeforeEach
    void setUp() {
        areaService = new SimpleAreaService(areaDao);
    }

    @Test
    void getArea() {
        Area area = TestingConstants.SAMPLE_AREA;
        when(areaDao.find(area.getAreaId())).thenReturn(area);

        Area actualArea = areaService.getArea(area.getAreaId());

        verify(areaDao).find(area.getAreaId());
        assertEquals(area, actualArea);
    }

    @Test
    void updateArea() {
        Area area = TestingConstants.SAMPLE_AREA;
        when(areaDao.updateEntity(area)).thenReturn(area);

        Area actualArea = areaService.updateArea(area);

        verify(areaDao).updateEntity(area);
        assertEquals(area, actualArea);
    }

    @Test
    void getAreas() {
        List<Area> areasInDb = List.of(Area.builder()
                        .areaId(GeneratingUtils.generateId())
                        .city("Kyiv")
                        .country("Ukraine")
                        .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                        .build(),
                TestingConstants.SAMPLE_AREA,
                TestingConstants.SAMPLE_AREA
        );
        Pageable pageable = PageRequest.of(1, 3);
        Page<Area> areaPage = new PageImpl<>(areasInDb.subList(1, 3), pageable, areasInDb.size());
        when(areaDao.findAll(pageable)).thenReturn(areaPage);

        Page<Area> actualPage = areaService.getAreas(pageable);

        verify(areaDao).findAll(pageable);
        assertEquals(areaPage, actualPage);
    }

    @Test
    void deleteArea() {
        String id = GeneratingUtils.generateId();
        when(areaDao.delete(id)).thenReturn(true);

        assertTrue(areaService.deleteArea(id));
        verify(areaDao).delete(id);
    }

    @Test
    void addArea() {
        Area area = TestingConstants.SAMPLE_AREA;
        when(areaDao.save(area)).thenReturn(area);

        Area actualArea = areaService.addArea(area);

        verify(areaDao).save(area);
        assertEquals(area, actualArea);
    }
}