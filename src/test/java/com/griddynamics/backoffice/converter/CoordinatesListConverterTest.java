package com.griddynamics.backoffice.converter;

import com.griddynamics.coordinates.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinatesListConverterTest {
    private final CoordinatesListConverter converter = new CoordinatesListConverter();

    @Test
    void convert() {
        List<Coordinates> coordinates = List.of(new Coordinates(12, 45), new Coordinates(13, 65));
        List<String> expected = List.of("{\"latitude\":12.0,\"longitude\":45.0}", "{\"latitude\":13.0,\"longitude\":65.0}");

        List<String> actual = converter.convert(coordinates);

        assertEquals(expected, actual);
    }

    @Test
    void unconvert() {
        List<String> stringsCoordinates = List.of("{\"latitude\":12.0,\"longitude\":45.0}", "{\"latitude\":13.0,\"longitude\":65.0}");
        List<Coordinates> expectedCoordinates = List.of(new Coordinates(12, 45), new Coordinates(13, 65));

        List<Coordinates> actualCoordinates = converter.unconvert(stringsCoordinates);

        assertEquals(expectedCoordinates, actualCoordinates);
    }
}