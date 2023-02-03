package com.griddynamics.backoffice.dao.area.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.ReadonlyBaseDaoDynamo;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.exception.ResourceNotFoundException;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.coordinates.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AreaDaoDynamoTest {
    private AreaDaoDynamo areaDaoDynamo;
    private final DynamoDB dynamoDB = mock(DynamoDB.class);
    private final DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        areaDaoDynamo = new AreaDaoDynamo(dynamoDBMapper, dynamoDB, objectMapper);
    }

    @Test
    void testSave() {
        Area area = TestingConstants.SAMPLE_AREA;
        when(dynamoDBMapper.load(area)).thenReturn(area);

        Area savedArea = areaDaoDynamo.save(area);

        verify(dynamoDBMapper).save(area);
        verify(dynamoDBMapper).load(area);
        assertEquals(savedArea, area);
    }

    @Test
    void testDeleteEntityExists() {
        String id = TestingConstants.SAMPLE_AREA.getAreaId();
        when(dynamoDBMapper.load(Area.class, id)).thenReturn(TestingConstants.SAMPLE_AREA);

        boolean deleteResult = areaDaoDynamo.delete(id);

        verify(dynamoDBMapper).load(Area.class, id);
        verify(dynamoDBMapper).delete(TestingConstants.SAMPLE_AREA);
        assertTrue(deleteResult);
    }

    @Test
    void testDeleteEntityNotExists() {
        String id = GeneratingUtils.generateId();
        when(dynamoDBMapper.load(Area.class, id)).thenReturn(null);

        Executable executable = () -> areaDaoDynamo.delete(id);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Area.class, id);
        verify(dynamoDBMapper, never()).delete(any());
    }

    @Test
    void testFindAll() {
        List<Area> expectedAreas = List.of(TestingConstants.SAMPLE_AREA,
                Area.builder()
                        .areaId(GeneratingUtils.generateId())
                        .city("Kyiv")
                        .country("Ukraine")
                        .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                        .build()
        );
        PaginatedScanList<Area> listMock = mock(PaginatedScanList.class);
        when(listMock.iterator()).thenReturn(expectedAreas.iterator());
        when(dynamoDBMapper.scan(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(listMock);

        List<Area> actualAreas = areaDaoDynamo.findAll();

        verify(dynamoDBMapper, times(1)).scan(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        assertEquals(expectedAreas, actualAreas);
    }

    @Test
    void testFindAllPaginated() {
        List<Area> areasInDb = List.of(Area.builder()
                        .areaId(GeneratingUtils.generateId())
                        .city("Kyiv")
                        .country("Ukraine")
                        .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                        .build(),
                TestingConstants.SAMPLE_AREA
        );
        List<Area> expectedContent = List.of(TestingConstants.SAMPLE_AREA);
        Pageable pageable = PageRequest.of(1, 1);
        PaginatedScanList listMock = mock(PaginatedScanList.class);
        when(dynamoDBMapper.scan(any(), any())).thenReturn(listMock);
        when(listMock.stream()).thenReturn(areasInDb.stream());
        when(dynamoDBMapper.count(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(areasInDb.size());

        Page<Area> areas = areaDaoDynamo.findAll(pageable);
        List<Area> actualContent = areas.getContent();

        verify(dynamoDBMapper).scan(any(), any());
        verify(dynamoDBMapper).count(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        assertEquals(pageable.getPageSize(), areas.getContent().size());
        assertEquals(expectedContent, actualContent);
    }


    void testFindAllInvalidPagination() {
        List<Area> areasInDb = List.of(Area.builder()
                        .areaId(GeneratingUtils.generateId())
                        .city("Kyiv")
                        .country("Ukraine")
                        .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                        .build(),
                TestingConstants.SAMPLE_AREA,
                TestingConstants.SAMPLE_AREA
        );

        Pageable pageable = PageRequest.of(2, 2);
        when(dynamoDBMapper.count(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(areasInDb.size());

        Executable executable = () -> areaDaoDynamo.findAll(pageable);

        assertThrows(PaginationException.class, executable);
        verify(dynamoDBMapper).count(Area.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        verify(dynamoDBMapper, never()).scan(any(), any());
    }

    @Test
    void testFind() {
        Area expectedArea = TestingConstants.SAMPLE_AREA;
        when(dynamoDBMapper.load(Area.class, expectedArea.getEntityId())).thenReturn(expectedArea);

        Area actualArea = areaDaoDynamo.find(expectedArea.getAreaId());

        verify(dynamoDBMapper).load(Area.class, expectedArea.getEntityId());
        assertEquals(expectedArea, actualArea);
    }

    @Test
    void testFindNonExistent() {
        String id = GeneratingUtils.generateId();
        when(dynamoDBMapper.load(Area.class, id)).thenReturn(null);

        Executable executable = () -> areaDaoDynamo.find(id);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Area.class, id);
    }

    @Test
    void testUpdateEntity() {
        Area update = TestingConstants.SAMPLE_AREA;
        when(dynamoDBMapper.load(Area.class, update.getAreaId())).thenReturn(
                Area.builder()
                        .areaId(update.getAreaId())
                        .city("Kyiv")
                        .country("Ukraine")
                        .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                        .build()
        );
        when(dynamoDBMapper.load(any())).thenReturn(update);

        Area actualArea = areaDaoDynamo.updateEntity(update);

        verify(dynamoDBMapper).load(Area.class, update.getAreaId());
        verify(dynamoDBMapper).load(any());
        verify(dynamoDBMapper).save(any());
        assertEquals(update, actualArea);
    }

    @Test
    void testUpdateEntity_ShouldFail() {
        Area update = TestingConstants.SAMPLE_AREA;
        when(dynamoDBMapper.load(Area.class, update.getAreaId())).thenReturn(Area.builder()
                .areaId(update.getAreaId())
                .city("Kyiv")
                .country("Ukraine")
                .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                .build());
        when(dynamoDBMapper.load(any())).thenReturn(null);

        assertThrows(DatabaseException.class, () -> areaDaoDynamo.updateEntity(update));
    }

    @Test
    void testUpdateNonExistent() {
        Area areaWithWrongId = Area.builder()
                .areaId(GeneratingUtils.generateId())
                .city("Kyiv")
                .country("Ukraine")
                .coordinates(List.of(new Coordinates(12.3, 15.6), new Coordinates(12.5, 16.5)))
                .build();
        when(dynamoDBMapper.load(Area.class, areaWithWrongId.getAreaId())).thenReturn(null);

        Executable executable = () -> areaDaoDynamo.updateEntity(areaWithWrongId);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Area.class, areaWithWrongId.getAreaId());
        verify(dynamoDBMapper, never()).load(any());
        verify(dynamoDBMapper, never()).save(any());
    }
}