package com.griddynamics.backoffice.model.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.griddynamics.backoffice.converter.CoordinatesListConverter;
import com.griddynamics.backoffice.model.IDocument;
import com.griddynamics.coordinates.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "areas")
@DynamoDBTable(tableName = "areas")
public class Area implements IDocument {
    @NonNull
    @DynamoDBHashKey(attributeName = "areaId")
    private String areaId;

    @NonNull
    @DynamoDBAttribute(attributeName = "country")
    private String country;

    @NonNull
    @DynamoDBAttribute(attributeName = "city")
    private String city;

    @NonNull
    @DynamoDBAttribute(attributeName = "coordinates")
    @DynamoDBTypeConverted(converter = CoordinatesListConverter.class)
    private List<Coordinates> coordinates;

    @Override
    @DynamoDBIgnore
    @JsonIgnore
    public String getEntityId() {
        return areaId;
    }
}
