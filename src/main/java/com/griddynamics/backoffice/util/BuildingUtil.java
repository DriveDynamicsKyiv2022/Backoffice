package com.griddynamics.backoffice.util;

import com.griddynamics.backoffice.dto.AreaDto;
import com.griddynamics.backoffice.dto.TariffDto;
import com.griddynamics.backoffice.model.Area;
import com.griddynamics.backoffice.model.Tariff;

import java.util.UUID;

public class BuildingUtil {
    public static TariffDto getDto(Tariff tariff) {
        return TariffDto.builder()
                .id(tariff.getId())
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

    public static Tariff getEntity(TariffDto tariffDto) {
        return Tariff.builder()
                .id(tariffDto.getId())
                .name(tariffDto.getName())
                .ratePerHour(tariffDto.getRatePerHour())
                .carBodyStyle(tariffDto.getCarBodyStyle())
                .description(tariffDto.getDescription())
                .build();
    }

    public static Area getEntity(AreaDto areaDto) {
        String areaId = areaDto.getAreaId() == null ? UUID.randomUUID().toString() : areaDto.getAreaId();
        return Area.builder()
                .areaId(areaId)
                .country(areaDto.getCountry())
                .city(areaDto.getCity())
                .coordinates(areaDto.getCoordinates())
                .build();
    }
}
