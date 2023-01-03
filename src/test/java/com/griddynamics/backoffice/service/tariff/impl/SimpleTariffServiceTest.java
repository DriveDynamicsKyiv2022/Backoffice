package com.griddynamics.backoffice.service.tariff.impl;

import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.car.enums.CarBodyStyle;
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

class SimpleTariffServiceTest {
    private SimpleTariffService tariffService;

    private final ITariffDao tariffDao = mock(ITariffDao.class);

    @BeforeEach
    void setUp() {
        tariffService = new SimpleTariffService(tariffDao);
    }

    @Test
    void getTariff() {
        Tariff tariff = TestingConstants.SAMPLE_TARIFF;
        when(tariffDao.find(tariff.getTariffId())).thenReturn(tariff);

        Tariff actualTariff = tariffService.getTariff(tariff.getTariffId());

        assertEquals(tariff, actualTariff);
        verify(tariffDao).find(tariff.getTariffId());
    }

    @Test
    void getTariffs() {
        List<Tariff> tariffList = List.of(TestingConstants.SAMPLE_TARIFF,
                Tariff.builder()
                        .tariffId(GeneratingUtils.generateId())
                        .name("Sport")
                        .ratePerHour(12)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .description("Sporty")
                        .build(),
                TestingConstants.SAMPLE_TARIFF);
        Pageable pageable = PageRequest.of(1, 2);
        Page<Tariff> tariffPage = new PageImpl<>(tariffList.subList(2, 3), pageable, tariffList.size());
        when(tariffDao.findAll(pageable)).thenReturn(tariffPage);

        Page<Tariff> actualPage = tariffService.getTariffs(pageable);

        verify(tariffDao).findAll(pageable);
        assertEquals(tariffPage, actualPage);
    }

    @Test
    void addTariff() {
        Tariff tariff = TestingConstants.SAMPLE_TARIFF;
        when(tariffDao.save(tariff)).thenReturn(tariff);

        Tariff actualTariff = tariffService.addTariff(tariff);

        verify(tariffDao).save(tariff);
        assertEquals(tariff, actualTariff);
    }

    @Test
    void updateTariff() {
        Tariff tariff = TestingConstants.SAMPLE_TARIFF;
        when(tariffDao.updateEntity(tariff)).thenReturn(tariff);

        Tariff actualTariff = tariffService.updateTariff(tariff);

        verify(tariffDao).updateEntity(tariff);
        assertEquals(tariff, actualTariff);
    }

    @Test
    void deleteTariff() {
        String id = GeneratingUtils.generateId();
        when(tariffDao.delete(id)).thenReturn(true);

        assertTrue(tariffService.deleteTariff(id));

        verify(tariffDao).delete(id);
    }
}