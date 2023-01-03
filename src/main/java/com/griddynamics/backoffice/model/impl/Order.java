package com.griddynamics.backoffice.model.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.griddynamics.backoffice.model.IDocument;
import com.griddynamics.car.enums.CarBodyStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "orders")
@DynamoDBTable(tableName = "orders")
public class Order implements IDocument {
    @JsonIgnore
    @DynamoDBIgnore
    public static final String USER_ID_INDEX_NAME = "userId-index";
    @JsonProperty(value = "orderId")
    @DynamoDBHashKey(attributeName = "orderId")
    private String orderId;

    @JsonProperty(value = "startDateTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @DynamoDBAttribute(attributeName = "startDateTimestamp")
    private Long startDateTimestamp;

    @JsonProperty(value = "endDateTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @DynamoDBAttribute(attributeName = "endDateTimestamp")
    private Long endDateTimestamp;

    @JsonProperty(value = "price")
    @DynamoDBAttribute(attributeName = "price")
    private Double price;

    @JsonProperty(value = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = USER_ID_INDEX_NAME)
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
