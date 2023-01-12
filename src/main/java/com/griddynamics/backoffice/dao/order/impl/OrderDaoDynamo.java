package com.griddynamics.backoffice.dao.order.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.dao.ReadonlyBaseDaoDynamo;
import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.DynamoLogicalOperator;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.request.AbstractOrderFilteringRequest;
import com.griddynamics.backoffice.model.request.ManagerOrderFilteringRequest;
import com.griddynamics.backoffice.model.request.UserOrderFilteringRequest;
import com.griddynamics.backoffice.util.DynamoDbUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.Set;

@Repository
@Profile("!local")
public class OrderDaoDynamo extends ReadonlyBaseDaoDynamo<Order> implements IOrderDao {

    @Autowired
    public OrderDaoDynamo(DynamoDBMapper dynamoDBMapper, DynamoDB dynamoDB, ObjectMapper objectMapper) {
        super(dynamoDBMapper, Order.class, "order", objectMapper, dynamoDB);
    }

    @Override
    public Page<Order> getUserOrdersHistoryPaginated(UserOrderFilteringRequest userOrderRequest, Pageable pageable) {
        ValueMap valueMap = new ValueMap();
        valueMap.withLong(":userId", userOrderRequest.getUserId());
        String keyConditionExpression = "userId = :userId";
        String filterExpression = generateFilterExpressionAndFillValueMap(userOrderRequest, valueMap);
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(keyConditionExpression)
                .withMaxPageSize(pageable.getPageSize())
                .withValueMap(valueMap);
        if (!filterExpression.isEmpty()) {
            querySpec.withFilterExpression(filterExpression);
        }
        return super.findByIndex(Order.USER_ID_INDEX_NAME, pageable, index -> index.query(querySpec));
    }

    @Override
    public Page<Order> getAllOrdersPaginated(ManagerOrderFilteringRequest managerOrderRequest, Pageable pageable) {
        ValueMap valueMap = new ValueMap();
        String filterExpression = Strings.EMPTY;
        if (managerOrderRequest.getUserIds() != null) {
            valueMap = buildValueMap(managerOrderRequest.getUserIds(), "user");
            filterExpression = buildMultipleValuesFilterExpression(managerOrderRequest.getUserIds(), "user", "userId");
        }
        filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression,
                generateFilterExpressionAndFillValueMap(managerOrderRequest, valueMap), DynamoLogicalOperator.AND);
        ScanSpec scanSpec = new ScanSpec();
        if (!filterExpression.isEmpty()) {
            scanSpec.withFilterExpression(filterExpression)
                    .withValueMap(valueMap);
        }

        return super.findByIndex(Order.USER_ID_INDEX_NAME, pageable, index -> index.scan(scanSpec));
    }

    private String buildMultipleValuesFilterExpression(Set<?> values, String valuesName, String attributeName) {
        StringBuilder builder = new StringBuilder();
        for (Object value : values) {
            builder.append(":").append(valuesName).append(value).append(",");
        }
        return attributeName + " IN (" + builder.toString().substring(0, builder.length() - 1) + ")";
    }

    private ValueMap buildValueMap(Set<?> values, String valuesName) {
        ValueMap valueMap = new ValueMap();
        return appendValueMap(values, valuesName, valueMap);
    }

    private ValueMap appendValueMap(Set<?> values, String valuesName, ValueMap valueMap) {
        for (Object value : values) {
            valueMap.withString(":" + valuesName + value, value.toString());
        }
        return valueMap;
    }


    private String generateFilterExpressionAndFillValueMap(AbstractOrderFilteringRequest orderRequest, ValueMap valueMap) {
        if (valueMap == null) {
            valueMap = new ValueMap();
        }
        String filterExpression = Strings.EMPTY;
        if (orderRequest.getStartDate() != null) {
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, "startDateTimestamp >= :startDateTimestamp",
                    DynamoLogicalOperator.AND);
            valueMap.withLong(":startDateTimestamp", orderRequest.getStartDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC));
        }
        if (orderRequest.getEndDate() != null) {
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, "endDateTimestamp <= :endDateTimestamp",
                    DynamoLogicalOperator.AND);
            valueMap.withLong(":endDateTimestamp", orderRequest.getEndDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC));
        }
        if (orderRequest.getCarBodyStyles() != null) {
            String multipleValuesFilterExpression = buildMultipleValuesFilterExpression(orderRequest.getCarBodyStyles(), "carBodyStyle",
                    "carBodyStyle");
            appendValueMap(orderRequest.getCarBodyStyles(), "carBodyStyle", valueMap);
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, multipleValuesFilterExpression,
                    DynamoLogicalOperator.AND);
        }
        return filterExpression;
    }
}
