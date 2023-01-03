package com.griddynamics.backoffice.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.griddynamics.backoffice.util.JsonUtils;
import com.griddynamics.coordinates.Coordinates;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinatesListConverter implements DynamoDBTypeConverter<List<String>, List<Coordinates>> {
    @Override
    public List<String> convert(List<Coordinates> coordinates) {
        return coordinates.stream().map(JsonUtils::toJson).collect(Collectors.toList());
    }

    @Override
    public List<Coordinates> unconvert(List<String> strings) {
        return strings.stream().map(s -> JsonUtils.fromJson(s, Coordinates.class)).collect(Collectors.toList());
    }
}
