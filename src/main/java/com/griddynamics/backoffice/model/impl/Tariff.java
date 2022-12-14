package com.griddynamics.backoffice.model.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.griddynamics.backoffice.model.IDocument;
import com.griddynamics.car.enums.CarBodyStyle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Document(collection = "tariffs")
@DynamoDBTable(tableName = "tariffs")
public class Tariff implements IDocument {
    @NonNull
    @DynamoDBHashKey(attributeName = "tariffId")
    private String tariffId;

    @NonNull
    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private double ratePerHour;

    @NonNull
    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private CarBodyStyle carBodyStyle;

    @NonNull
    @DynamoDBAttribute
    private String description;

    @Override
    @DynamoDBIgnore
    @JsonIgnore
    public String getEntityId() {
        return tariffId;
    }
}
