package com.griddynamics.backoffice.dao.tariff.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.ReadonlyBaseDaoDynamo;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.exception.ResourceNotFoundException;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.car.enums.CarBodyStyle;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TariffDaoDynamoTest {
    private TariffDaoDynamo tariffDaoDynamo;
    private final DynamoDB dynamoDB = mock(DynamoDB.class);
    private final DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        tariffDaoDynamo = new TariffDaoDynamo(dynamoDBMapper, objectMapper, dynamoDB);
    }

    @Test
    void testSave() {
        Tariff tariff = TestingConstants.SAMPLE_TARIFF;
        when(dynamoDBMapper.load(tariff)).thenReturn(tariff);

        var savedTariff = tariffDaoDynamo.save(tariff);

        verify(dynamoDBMapper).load(tariff);
        verify(dynamoDBMapper).save(tariff);
        assertEquals(tariff, savedTariff);
    }

    @Test
    void testDeleteEntityExists() {
        String id = TestingConstants.SAMPLE_TARIFF.getTariffId();
        when(dynamoDBMapper.load(Tariff.class, id)).thenReturn(TestingConstants.SAMPLE_TARIFF);

        tariffDaoDynamo.delete(id);

        verify(dynamoDBMapper).load(Tariff.class, id);
        verify(dynamoDBMapper).delete(TestingConstants.SAMPLE_TARIFF);
    }

    @Test
    void testDeleteEntityNotExists() {
        String id = GeneratingUtils.generateId();
        when(dynamoDBMapper.load(Tariff.class, id)).thenReturn(null);

        Executable executable = () -> tariffDaoDynamo.delete(id);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Tariff.class, id);
        verify(dynamoDBMapper, never()).delete(any());
    }

    @Test
    void testFindAll() {
        List<Tariff> expectedTariffs = List.of(TestingConstants.SAMPLE_TARIFF,
                Tariff.builder()
                        .tariffId(GeneratingUtils.generateId())
                        .name("Sport")
                        .ratePerHour(12)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .description("Sporty")
                        .build());
        PaginatedScanList<Tariff> listMock = mock(PaginatedScanList.class);
        when(listMock.iterator()).thenReturn(expectedTariffs.iterator());
        when(dynamoDBMapper.scan(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(listMock);

        List<Tariff> actualTariffs = tariffDaoDynamo.findAll();

        verify(dynamoDBMapper, times(1)).scan(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        assertEquals(expectedTariffs, actualTariffs);
    }

    @Test
    void testFindAllPaginated() {
        List<Tariff> tariffsInDb = List.of(TestingConstants.SAMPLE_TARIFF,
                Tariff.builder()
                        .tariffId(GeneratingUtils.generateId())
                        .name("Sport")
                        .ratePerHour(12)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .description("Sporty")
                        .build());
        List<Tariff> expectedContent = List.of(TestingConstants.SAMPLE_TARIFF);
        Pageable pageable = PageRequest.of(0, 1);
        PaginatedScanList listMock = mock(PaginatedScanList.class);
        when(dynamoDBMapper.scan(any(), any()))
                .thenReturn(listMock);
        when(listMock.stream()).thenReturn(tariffsInDb.stream());
        when(dynamoDBMapper.count(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(tariffsInDb.size());

        Page<Tariff> tariffPage = tariffDaoDynamo.findAll(pageable);
        List<Tariff> actualContent = tariffPage.getContent();

        verify(dynamoDBMapper).scan(any(), any());
        verify(dynamoDBMapper).count(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        assertEquals(pageable.getPageSize(), tariffPage.getContent().size());
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testFindAllInvalidPagination() {
        List<Tariff> tariffsInDb = List.of(TestingConstants.SAMPLE_TARIFF,
                Tariff.builder()
                        .tariffId(GeneratingUtils.generateId())
                        .name("Sport")
                        .ratePerHour(12)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .description("Sporty")
                        .build(),
                TestingConstants.SAMPLE_TARIFF);

        Pageable pageable = PageRequest.of(2, 2);
        when(dynamoDBMapper.count(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(tariffsInDb.size());

        Executable executable = () -> tariffDaoDynamo.findAll(pageable);

        assertThrows(PaginationException.class, executable);
        verify(dynamoDBMapper).count(Tariff.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        verify(dynamoDBMapper, never()).scan(any(), any());
    }

    @Test
    void testFind() {
        Tariff expectedTariff = TestingConstants.SAMPLE_TARIFF;
        when(dynamoDBMapper.load(Tariff.class, expectedTariff.getEntityId())).thenReturn(expectedTariff);

        Tariff actualTariff = tariffDaoDynamo.find(expectedTariff.getTariffId());

        verify(dynamoDBMapper).load(Tariff.class, expectedTariff.getEntityId());
        assertEquals(expectedTariff, actualTariff);
    }

    @Test
    void testFindNonExistent() {
        String id = GeneratingUtils.generateId();
        when(dynamoDBMapper.load(Tariff.class, id)).thenReturn(null);

        Executable executable = () -> tariffDaoDynamo.find(id);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Tariff.class, id);
    }

    @Test
    void testUpdateEntity() {
        Tariff update = TestingConstants.SAMPLE_TARIFF;
        when(dynamoDBMapper.load(Tariff.class, update.getTariffId())).thenReturn(
                Tariff.builder()
                        .tariffId(GeneratingUtils.generateId())
                        .name("Sport")
                        .ratePerHour(12)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .description("Sporty")
                        .build()
        );
        when(dynamoDBMapper.load(any())).thenReturn(update);

        Tariff actualTariff = tariffDaoDynamo.updateEntity(update);

        verify(dynamoDBMapper).load(Tariff.class, update.getTariffId());
        verify(dynamoDBMapper).load(any());
        verify(dynamoDBMapper).save(any());
        assertEquals(update, actualTariff);
    }

    @Test
    void testUpdateEntity_ShouldFail() {
        Tariff update = TestingConstants.SAMPLE_TARIFF;
        when(dynamoDBMapper.load(Tariff.class, update.getTariffId())).thenReturn(Tariff.builder()
                .tariffId(GeneratingUtils.generateId())
                .name("Sport")
                .ratePerHour(12)
                .carBodyStyle(CarBodyStyle.CABRIOLET)
                .description("Sporty")
                .build());
        when(dynamoDBMapper.load(any())).thenReturn(null);

        assertThrows(DatabaseException.class, () -> tariffDaoDynamo.updateEntity(update));
    }

    @Test
    void testUpdateNonExistent() {
        Tariff tariffWithWrongId = Tariff.builder()
                .tariffId(GeneratingUtils.generateId())
                .name("Sport")
                .ratePerHour(12)
                .carBodyStyle(CarBodyStyle.CABRIOLET)
                .description("Sporty")
                .build();
        when(dynamoDBMapper.load(Tariff.class, tariffWithWrongId.getTariffId())).thenReturn(null);

        Executable executable = () -> tariffDaoDynamo.updateEntity(tariffWithWrongId);

        assertThrows(ResourceNotFoundException.class, executable);
        verify(dynamoDBMapper).load(Tariff.class, tariffWithWrongId.getTariffId());
        verify(dynamoDBMapper, never()).load(any());
        verify(dynamoDBMapper, never()).save(any());
    }
}