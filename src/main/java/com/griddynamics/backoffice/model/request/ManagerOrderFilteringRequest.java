package com.griddynamics.backoffice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ManagerOrderFilteringRequest extends AbstractOrderFilteringRequest {
    @Nullable
    @Size(min = 1, message = "At least one user must be provided")
    private Set<Long> userIds;
}