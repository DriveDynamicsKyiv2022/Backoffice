package com.griddynamics.backoffice.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.coordinates.Coordinates;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinatesListConverter implements DynamoDBTypeConverter<List<String>, List<Coordinates>> {
    private final ObjectMapper objectMapper;

    public CoordinatesListConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<String> convert(List<Coordinates> coordinates) {
        return coordinates.stream().map(coord -> {
            try {
                return objectMapper.writeValueAsString(coord);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<Coordinates> unconvert(List<String> strings) {
        return strings.stream().map(s -> {
            try {
                return objectMapper.readValue(s, Coordinates.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
