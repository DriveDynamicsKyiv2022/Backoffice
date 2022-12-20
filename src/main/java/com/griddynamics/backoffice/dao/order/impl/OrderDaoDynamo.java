package com.griddynamics.backoffice.dao.order.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.dao.ReadonlyBaseDaoDynamo;
import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.util.DynamoDbUtils;
import com.griddynamics.request.AbstractOrderFilteringRequest;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
        return super.findByIndex(Order.userIdIndexName, pageable, index -> index.query(querySpec));
    }

    @Override
    public Page<Order> getAllOrdersPaginated(ManagerOrderFilteringRequest managerOrderRequest, Pageable pageable) {
        ValueMap valueMap = new ValueMap();
        String filterExpression = Strings.EMPTY;
        if (managerOrderRequest.getUserIds() != null) {
            valueMap.withList(":userIds", managerOrderRequest.getUserIds());
            filterExpression = "userId IN (:userIds)";
        }
        filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression,
                generateFilterExpressionAndFillValueMap(managerOrderRequest, valueMap), "AND");
        ScanSpec scanSpec = new ScanSpec();
        if (!filterExpression.isEmpty()) {
            scanSpec.withFilterExpression(filterExpression)
                    .withValueMap(valueMap);
        }

        return super.findByIndex(Order.userIdIndexName, pageable, index -> index.scan(scanSpec));
    }


    private String generateFilterExpressionAndFillValueMap(AbstractOrderFilteringRequest orderRequest, ValueMap valueMap) {
        if (valueMap == null) {
            valueMap = new ValueMap();
        }
        String filterExpression = Strings.EMPTY;
        if (orderRequest.getStartDate() != null) {
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, "startDateTimestamp >= :startDateTimestamp",
                    "AND");
            valueMap.withLong(":startDateTimestamp", orderRequest.getStartDate().toEpochDay());
        }
        if (orderRequest.getEndDate() != null) {
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, "endDateTimestamp <= :endDateTimestamp",
                    "AND");
            valueMap.withLong(":endDateTimestamp", orderRequest.getEndDate().toEpochDay());
        }
        if (orderRequest.getCarBodyStyle() != null) {
            filterExpression = DynamoDbUtils.appendFilterExpression(filterExpression, "carBodyStyle = :carBodyStyle",
                    "AND");
            valueMap.with(":carBodyStyle", orderRequest.getCarBodyStyle().name());
        }
        return filterExpression;
    }
}
