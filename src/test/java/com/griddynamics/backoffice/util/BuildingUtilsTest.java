package com.griddynamics.backoffice.util;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.order.OrderDto;
import com.griddynamics.tariff.TariffDto;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BuildingUtilsTest {

    @Test
    void testGetOrderDto() {
        Order order = TestingConstants.SAMPLE_ORDER;

        OrderDto orderDto = BuildingUtils.getDto(order);
        boolean equality = Objects.equals(orderDto.getOrderId(), order.getOrderId()) &&
                Objects.equals(orderDto.getCarBodyStyle(), order.getCarBodyStyle()) &&
                Objects.equals(orderDto.getCarId(), order.getCarId()) &&
                Objects.equals(orderDto.getPrice(), order.getPrice()) &&
                Objects.equals(orderDto.getEndDateTime().toEpochSecond(ZoneOffset.UTC), order.getEndDateTimestamp()) &&
                Objects.equals(orderDto.getStartDateTime().toEpochSecond(ZoneOffset.UTC), order.getStartDateTimestamp()) &&
                Objects.equals(orderDto.getUserId(), order.getUserId()) &&
                Objects.equals(orderDto.getTariffId(), order.getTariffId());

        assertTrue(equality);
    }

    @Test
    void testGetAreaDto() {
        Area area = TestingConstants.SAMPLE_AREA;

        AreaDto areaDto = BuildingUtils.getDto(area);
        boolean equality = Objects.equals(area.getAreaId(), areaDto.getAreaId()) &&
                Objects.equals(area.getCity(), areaDto.getCity()) &&
                Objects.equals(area.getCoordinates(), areaDto.getCoordinates()) &&
                Objects.equals(area.getCountry(), areaDto.getCountry());

        assertTrue(equality);
    }

    @Test
    void testGetTariffDto() {
        Tariff tariff = TestingConstants.SAMPLE_TARIFF;

        TariffDto tariffDto = BuildingUtils.getDto(tariff);
        boolean equality = Objects.equals(tariffDto.getTariffId(), tariff.getTariffId()) &&
                Objects.equals(tariffDto.getCarBodyStyle(), tariff.getCarBodyStyle()) &&
                Objects.equals(tariffDto.getDescription(), tariff.getDescription()) &&
                Objects.equals(tariffDto.getName(), tariff.getName()) &&
                Objects.equals(tariffDto.getRatePerHour(), tariff.getRatePerHour());

        assertTrue(equality);
    }

    @Test
    void testGetTariff() {
        TariffDto tariffDto = TestingConstants.SAMPLE_TARIFF_DTO_NO_ID;

        Tariff tariff = BuildingUtils.getEntity(tariffDto);
        boolean equality = Objects.equals(tariffDto.getCarBodyStyle(), tariff.getCarBodyStyle()) &&
                Objects.equals(tariffDto.getDescription(), tariff.getDescription()) &&
                Objects.equals(tariffDto.getName(), tariff.getName()) &&
                Objects.equals(tariffDto.getRatePerHour(), tariff.getRatePerHour());

        assertTrue(equality && tariff.getTariffId() != null);
    }

    @Test
    void testGetTariffWithSpecifiedId() {
        TariffDto tariffDto = TestingConstants.SAMPLE_TARIFF_DTO_NO_ID;
        String id = GeneratingUtils.generateId();

        Tariff tariff = BuildingUtils.getEntity(tariffDto, id);
        boolean equality = Objects.equals(tariffDto.getCarBodyStyle(), tariff.getCarBodyStyle()) &&
                Objects.equals(tariffDto.getDescription(), tariff.getDescription()) &&
                Objects.equals(tariffDto.getName(), tariff.getName()) &&
                Objects.equals(tariffDto.getRatePerHour(), tariff.getRatePerHour());

        assertTrue(equality && tariff.getTariffId().equals(id));
    }

    @Test
    void testGetArea() {
        AreaDto areaDto = TestingConstants.SAMPLE_AREA_DTO_NO_ID;

        Area area = BuildingUtils.getEntity(areaDto);
        boolean equality = Objects.equals(area.getCity(), areaDto.getCity()) &&
                Objects.equals(area.getCoordinates(), areaDto.getCoordinates()) &&
                Objects.equals(area.getCountry(), areaDto.getCountry());

        assertTrue(equality && area.getAreaId() != null);
    }

    @Test
    void testGetAreaWithSpecifiedId() {
        AreaDto areaDto = TestingConstants.SAMPLE_AREA_DTO_NO_ID;
        String id = GeneratingUtils.generateId();

        Area area = BuildingUtils.getEntity(areaDto, id);
        boolean equality = Objects.equals(area.getCity(), areaDto.getCity()) &&
                Objects.equals(area.getCoordinates(), areaDto.getCoordinates()) &&
                Objects.equals(area.getCountry(), areaDto.getCountry());

        assertTrue(equality && area.getAreaId().equals(id));
    }
}