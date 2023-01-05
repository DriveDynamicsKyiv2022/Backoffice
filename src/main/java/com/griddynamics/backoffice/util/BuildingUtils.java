package com.griddynamics.backoffice.util;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.order.OrderDto;
import com.griddynamics.tariff.TariffDto;

public class BuildingUtils {
    public static TariffDto getDto(Tariff tariff) {
        return TariffDto.builder()
                .tariffId(tariff.getTariffId())
                .name(tariff.getName())
                .ratePerHour(tariff.getRatePerHour())
                .carBodyStyle(tariff.getCarBodyStyle())
                .description(tariff.getDescription())
                .build();
    }

    public static AreaDto getDto(Area area) {
        return AreaDto.builder()
                .areaId(area.getAreaId())
                .country(area.getCountry())
                .city(area.getCity())
                .coordinates(area.getCoordinates())
                .build();
    }


    public static OrderDto getDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .startDateTime(DateTimeUtils.fromTimestamp(order.getStartDateTimestamp()))
                .endDateTime(DateTimeUtils.fromTimestamp(order.getEndDateTimestamp()))
                .tariffId(order.getTariffId())
                .carId(order.getCarId())
                .userId(order.getUserId())
                .price(order.getPrice())
                .carBodyStyle(order.getCarBodyStyle())
                .build();
    }


    public static Tariff getEntity(TariffDto tariffDto) {
        if (tariffDto.getTariffId() != null) {
            throw new IllegalArgumentException("Id must not be specified in this context");
        }
        String tariffId = GeneratingUtils.generateId();
        return getEntity(tariffDto, tariffId);
    }

    public static Tariff getEntity(TariffDto tariffDto, String id) {
        return Tariff.builder()
                .tariffId(id)
                .name(tariffDto.getName())
                .ratePerHour(tariffDto.getRatePerHour())
                .carBodyStyle(tariffDto.getCarBodyStyle())
                .description(tariffDto.getDescription())
                .build();
    }

    public static Area getEntity(AreaDto areaDto) {
        if (areaDto.getAreaId() != null) {
            throw new IllegalArgumentException("Id must not be specified in this context");
        }
        String areaId = GeneratingUtils.generateId();
        return getEntity(areaDto, areaId);
    }

    public static Area getEntity(AreaDto areaDto, String id) {
        return Area.builder()
                .areaId(id)
                .country(areaDto.getCountry())
                .city(areaDto.getCity())
                .coordinates(areaDto.getCoordinates())
                .build();
    }
}
