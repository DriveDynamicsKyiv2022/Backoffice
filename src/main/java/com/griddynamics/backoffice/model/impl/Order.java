package com.griddynamics.backoffice.model.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.griddynamics.backoffice.model.IDocument;
import com.griddynamics.car.enums.CarBodyStyle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Document(collection = "orders")
@DynamoDBTable(tableName = "orders")
public class Order implements IDocument {
    @JsonIgnore
    @DynamoDBIgnore
    public static final String userIdIndexName = "userId-index";
    @JsonProperty(value = "orderId")
    @DynamoDBHashKey(attributeName = "orderId")
    private String orderId;

    @JsonProperty(value = "startDateTimestamp")
    @DynamoDBAttribute(attributeName = "startDateTimestamp")
    private Long startDateTimestamp;

    @JsonProperty(value = "endDateTimestamp")
    @DynamoDBAttribute(attributeName = "endDateTimestamp")
    private Long endDateTimestamp;

    @JsonProperty(value = "price")
    @DynamoDBAttribute(attributeName = "price")
    private Double price;

    @JsonProperty(value = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = userIdIndexName)
    private Long userId;

    @JsonProperty(value = "carId")
    @DynamoDBAttribute(attributeName = "carId")
    private Long carId;

    @JsonProperty(value = "tariffId")
    @DynamoDBAttribute(attributeName = "tariffId")
    private String tariffId;

    @JsonProperty(value = "carBodyStyle")
    @DynamoDBTypeConvertedEnum
    private CarBodyStyle carBodyStyle;

    @Override
    @DynamoDBIgnore
    @JsonIgnore
    public String getEntityId() {
        return orderId;
    }
}
