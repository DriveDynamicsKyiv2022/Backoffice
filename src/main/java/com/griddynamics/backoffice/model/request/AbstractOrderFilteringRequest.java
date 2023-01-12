package com.griddynamics.backoffice.model.request;

import com.griddynamics.car.enums.CarBodyStyle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public abstract class AbstractOrderFilteringRequest {
    @Nullable
    protected LocalDate startDate;
    @Nullable
    protected LocalDate endDate;
    @Nullable
    protected Set<CarBodyStyle> carBodyStyles;
}
