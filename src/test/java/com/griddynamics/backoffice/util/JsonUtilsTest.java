package com.griddynamics.backoffice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.griddynamics.coordinates.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonUtilsTest {

    @Test
    void fromJson() {
        Coordinates expectedCoordinates = new Coordinates(12, 34);
        String coordString = "{\"latitude\":12,\"longitude\":34}";

        Coordinates actualCoordinates = JsonUtils.fromJson(coordString, Coordinates.class);

        assertEquals(expectedCoordinates, actualCoordinates);
    }

    @Test
    void fromJson_ShouldFail() {
        String coordString = "{\"latitude:12 \"longitude\":34}";

        assertThrows(RuntimeException.class, () -> JsonUtils.fromJson(coordString, Coordinates.class));
    }

    @Test
    void toJson() {
        Coordinates coordinates = new Coordinates(23.7, 56.3);
        String expectedStr = "{\"latitude\":23.7,\"longitude\":56.3}";

        String actualStr = JsonUtils.toJson(coordinates);

        assertEquals(expectedStr, actualStr);
        assertDoesNotThrow(() -> JsonUtils.toJson(coordinates));
    }
}