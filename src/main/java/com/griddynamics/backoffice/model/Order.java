package com.griddynamics.backoffice.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "Order")
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @NonNull
    private Long id;
    @Column(name = "start_date_time", nullable = false)
    @NonNull
    private LocalDateTime startDateTime;
    @Column(name = "end_date_time")
    @Nullable
    private LocalDateTime endDateTime;
    @Column(name = "price")
    @Nullable
    private Double price;
    @Column(name = "user_id", nullable = false)
    @NonNull
    private Long userId;
    @Column(name = "car_id", nullable = false)
    @NonNull
    private Long carId;
    @Column(name = "tariff_id", nullable = false)
    @NonNull
    private Long tariffId;
}
