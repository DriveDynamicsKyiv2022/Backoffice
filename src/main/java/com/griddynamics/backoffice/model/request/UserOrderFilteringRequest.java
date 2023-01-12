package com.griddynamics.backoffice.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class UserOrderFilteringRequest extends AbstractOrderFilteringRequest {
    @NotNull(message = "User id is mandatory")
    private long userId;
}
