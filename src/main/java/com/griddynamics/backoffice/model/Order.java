package com.griddynamics.backoffice.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "orders")
public class Order {
    @NonNull
    private Long id;
    @NonNull
    private LocalDateTime startDateTime;
    @Nullable
    private LocalDateTime endDateTime;
    @Nullable
    private Double price;
    @NonNull
    private Long userId;
    @NonNull
    private Long carId;
    @NonNull
    private String tariffId;
}
