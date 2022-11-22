package com.griddynamics.backoffice.model;

import com.griddynamics.carservice.enums.CarBodyStyle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tariffs")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;
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
