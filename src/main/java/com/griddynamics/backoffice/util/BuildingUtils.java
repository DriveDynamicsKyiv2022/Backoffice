package com.griddynamics.backoffice.util;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.order.OrderDto;
import com.griddynamics.tariff.TariffDto;
import org.springframework.lang.Nullable;

import java.util.UUID;

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
        try {
            String tariffId = getOrGenerateId(tariffDto.getTariffId());
            return Tariff.builder()
                    .tariffId(tariffId)
                    .name(tariffDto.getName())
                    .ratePerHour(tariffDto.getRatePerHour())
                    .carBodyStyle(tariffDto.getCarBodyStyle())
                    .description(tariffDto.getDescription())
                    .build();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Area getEntity(AreaDto areaDto) {
        try {
            String areaId = getOrGenerateId(areaDto.getAreaId());
            return Area.builder()
                    .areaId(areaId)
                    .country(areaDto.getCountry())
                    .city(areaDto.getCity())
                    .coordinates(areaDto.getCoordinates())
                    .build();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static String getOrGenerateId(@Nullable String id) {
        return id == null ? UUID.randomUUID().toString() : id;
    }
}
