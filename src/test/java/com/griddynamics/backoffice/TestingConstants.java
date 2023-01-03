package com.griddynamics.backoffice;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.car.enums.CarBodyStyle;
import com.griddynamics.coordinates.Coordinates;
import com.griddynamics.order.OrderDto;
import com.griddynamics.tariff.TariffDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TestingConstants {
    public static final Area SAMPLE_AREA = Area.builder()
            .areaId(GeneratingUtils.generateId())
            .city("Kharkiv")
            .country("Ukraine")
            .coordinates(List.of(new Coordinates(14.1, 14.5),
                    new Coordinates(14.2, 14.5)))
            .build();

    public static final Order SAMPLE_ORDER = Order.builder()
            .orderId(GeneratingUtils.generateId())
            .startDateTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .endDateTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .carBodyStyle(CarBodyStyle.CUV)
            .carId(2L)
            .userId(1L)
            .price(2.3)
            .tariffId(GeneratingUtils.generateId())
            .build();

    public static final Tariff SAMPLE_TARIFF = Tariff.builder()
            .tariffId(GeneratingUtils.generateId())
            .name("Daily")
            .ratePerHour(14)
            .carBodyStyle(CarBodyStyle.SEDAN)
            .description("Nice for daily-drive goals")
            .build();

    public static final TariffDto SAMPLE_TARIFF_DTO_NO_ID = TariffDto.builder()
            .name("Daily")
            .ratePerHour(14)
            .carBodyStyle(CarBodyStyle.SEDAN)
            .description("Nice for daily-drive goals")
            .build();

    public static final OrderDto SAMPLE_ORDER_DTO_NO_ID = OrderDto.builder()
            .startDateTime(LocalDateTime.now())
            .endDateTime(LocalDateTime.now())
            .carBodyStyle(CarBodyStyle.CUV)
            .carId(1L)
            .userId(2L)
            .price(2.3)
            .tariffId(GeneratingUtils.generateId())
            .build();

    public static final AreaDto SAMPLE_AREA_DTO_NO_ID = AreaDto.builder()
            .city("Kharkiv")
            .country("Ukraine")
            .coordinates(List.of(new Coordinates(14.1, 14.5),
                    new Coordinates(14.2, 14.5)))
            .build();
}
