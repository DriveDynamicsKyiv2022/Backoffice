package com.griddynamics.backoffice.controller;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.coordinates.Coordinates;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AreaControllerTest {
    private AreaController areaController;
    private final IAreaService areaService = mock(IAreaService.class);
    private final UriComponentsBuilder uriComponentsBuilder = mock(UriComponentsBuilder.class);
    private final UriComponents uriComponents = mock(UriComponents.class);

    @BeforeEach
    void setUp() {
        areaController = new AreaController(areaService);
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
        when(areaService.getAreas(any())).thenReturn(areaPage);
        when(uriComponentsBuilder.pathSegment(any())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.cloneBuilder()).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), any(Object.class))).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        when(uriComponents.toUriString()).thenReturn(null);

        ResponseEntity<Page<AreaDto>> responseEntity = areaController
                .getAreas(pageable.getPageNumber(), pageable.getPageSize(), uriComponentsBuilder);

        Page<AreaDto> expectedPage = areaPage.map(BuildingUtils::getDto);

        List<AreaDto> expectedContent = expectedPage.getContent();
        List<AreaDto> actualContent = responseEntity.getBody().getContent();

        AreaDto expected;
        AreaDto actual;
        for (int i = 0; i < expectedContent.size(); i++) {
            expected = expectedContent.get(i);
            actual = actualContent.get(i);
            assertEquals(expected, actual);
        }
        verify(areaService).getAreas(any());
    }

    @Test
    void getAreasInvalidParams() {
        assertThrows(PaginationException.class, () -> areaController.getAreas(null, 3, uriComponentsBuilder));
        verify(areaService, never()).getAreas(any());
    }

    @Test
    void addArea() {
        AreaDto areaDto = TestingConstants.SAMPLE_AREA_DTO_NO_ID;
        Area area = BuildingUtils.getEntity(areaDto);
        AreaDto expected = BuildingUtils.getDto(area);
        when(areaService.addArea(any())).thenReturn(area);
        when(uriComponentsBuilder.pathSegment(any())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(URI.create(Strings.EMPTY));

        ResponseEntity<AreaDto> areaDtoResponseEntity = areaController.addArea(areaDto, uriComponentsBuilder);
        AreaDto actual = areaDtoResponseEntity.getBody();

        assertEquals(expected, actual);
        verify(areaService).addArea(any());
    }

    @Test
    void getArea() {
        Area area = TestingConstants.SAMPLE_AREA;
        String id = area.getAreaId();
        AreaDto expected = BuildingUtils.getDto(area);
        when(areaService.getArea(id)).thenReturn(area);

        AreaDto actual = areaController.getArea(id);

        verify(areaService).getArea(id);
        assertEquals(expected, actual);
    }

    @Test
    void updateArea() {
        Area area = TestingConstants.SAMPLE_AREA;
        String id = area.getAreaId();
        AreaDto expected = BuildingUtils.getDto(area);
        when(areaService.updateArea(area)).thenReturn(area);

        AreaDto actual = areaController.updateArea(expected, id);

        verify(areaService).updateArea(area);
        assertEquals(expected, actual);
    }

    @Test
    void deleteArea() {
        String id = GeneratingUtils.generateId();
        when(areaService.deleteArea(id)).thenReturn(true);

        var simpleResponseResponseEntity = areaController.deleteArea(id);

        assertNotNull(simpleResponseResponseEntity.getBody());
        assertEquals(HttpStatus.OK, simpleResponseResponseEntity.getStatusCode());
    }
}