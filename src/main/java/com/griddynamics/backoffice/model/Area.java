package com.griddynamics.backoffice.model;

import com.griddynamics.common.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Document(collection = "areas")
public class Area {
    @Indexed(unique = true)
    private String areaId;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private List<Coordinates> coordinates;
}
