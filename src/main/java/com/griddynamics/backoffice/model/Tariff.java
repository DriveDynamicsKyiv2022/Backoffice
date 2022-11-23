package com.griddynamics.backoffice.model;

import com.griddynamics.carservice.enums.CarBodyStyle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Document(collection = "tariffs")
public class Tariff {
    public static final String ID_FIELD_NAME = "tariffId";
    @NonNull
    private String tariffId;
    @NonNull
    @Setter
    private String name;
    @Setter
    @Column(name = "rate_per_hour")
    private double ratePerHour;
    @NonNull
    @Column(name = "car_body_style")
    @Enumerated(EnumType.STRING)
    @Setter
    private CarBodyStyle carBodyStyle;
    @NonNull
    @Setter
    private String description;
}
