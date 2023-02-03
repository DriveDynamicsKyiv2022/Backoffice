package com.griddynamics.backoffice.dao.order.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.internal.PageIterable;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.ReadonlyBaseDaoDynamo;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.util.GeneratingUtils;
import com.griddynamics.car.enums.CarBodyStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderDaoDynamoTest {
    private OrderDaoDynamo orderDaoDynamo;
    private final DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
    private final DynamoDB dynamoDB = mock(DynamoDB.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        orderDaoDynamo = new OrderDaoDynamo(dynamoDBMapper, dynamoDB, objectMapper);
    }

    @Test
    void testFindAll() {
        List<Order> expectedOrders = List.of(TestingConstants.SAMPLE_ORDER);
        PaginatedScanList<Order> listMock = mock(PaginatedScanList.class);
        when(listMock.iterator()).thenReturn(expectedOrders.iterator());
        when(dynamoDBMapper.scan(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(listMock);

        List<Order> actualOrders = orderDaoDynamo.findAll();

        verify(dynamoDBMapper, times(1)).scan(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void testFindAllPaginated() {
        List<Order> ordersInDb = List.of(TestingConstants.SAMPLE_ORDER,
                Order.builder()
                        .orderId(GeneratingUtils.generateId())
                        .price(34.5)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .carId(5L)
                        .userId(6L)
                        .tariffId(GeneratingUtils.generateId())
                        .endDateTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                        .startDateTimestamp(LocalDateTime.now().plusHours(5).toEpochSecond(ZoneOffset.UTC))
                        .build());
        List<Order> expectedContent = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 1);
        PaginatedScanList listMock = mock(PaginatedScanList.class);
        when(dynamoDBMapper.scan(any(), any())).thenReturn(listMock);
        when(listMock.stream()).thenReturn(ordersInDb.stream());
        when(dynamoDBMapper.count(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(ordersInDb.size());

        Page<Order> orderPage = orderDaoDynamo.findAll(pageable);
        List<Order> actualContent = orderPage.getContent();

        verify(dynamoDBMapper).count(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        verify(dynamoDBMapper, times(1)).scan(any(), any());
        assertEquals(expectedContent, actualContent);
    }


    void testFindAllInvalidPagination() {
        List<Order> ordersInDb = List.of(TestingConstants.SAMPLE_ORDER,
                Order.builder()
                        .orderId(GeneratingUtils.generateId())
                        .price(34.5)
                        .carBodyStyle(CarBodyStyle.CABRIOLET)
                        .carId(5L)
                        .userId(6L)
                        .tariffId(GeneratingUtils.generateId())
                        .endDateTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                        .startDateTimestamp(LocalDateTime.now().plusHours(5).toEpochSecond(ZoneOffset.UTC))
                        .build());
        List<Order> expectedContent = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(5, 1);
        PaginatedScanList listMock = mock(PaginatedScanList.class);
        when(dynamoDBMapper.count(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION))
                .thenReturn(ordersInDb.size());

        assertThrows(PaginationException.class, () -> orderDaoDynamo.findAll(pageable));
        verify(dynamoDBMapper).count(Order.class, ReadonlyBaseDaoDynamo.DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        verify(dynamoDBMapper, never()).scan(any(), any());
    }

    @Test
    void testFind() {
        Order expectedOrder = TestingConstants.SAMPLE_ORDER;
        when(dynamoDBMapper.load(Order.class, expectedOrder.getOrderId())).thenReturn(expectedOrder);

        Order actualOrder = orderDaoDynamo.find(expectedOrder.getOrderId());

        verify(dynamoDBMapper).load(Order.class, expectedOrder.getOrderId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void testFindByIndex() {
        Table tableMock = mock(Table.class);
        Index indexMock = mock(Index.class);
        PageIterable<Item, QueryOutcome> pagesMock = mock(PageIterable.class);
        ItemCollection<QueryOutcome> itemCollectionMock = mock(ItemCollection.class);
        QuerySpec querySpec = new QuerySpec();
        when(dynamoDB.getTable(any())).thenReturn(tableMock);
        when(tableMock.getIndex(any())).thenReturn(indexMock);
        when(indexMock.query(querySpec)).thenReturn(itemCollectionMock);
        when(itemCollectionMock.pages()).thenReturn(pagesMock);

    }

    @Test
    void getUserOrdersHistoryPaginated() {

    }

    @Test
    void getAllOrdersPaginated() {
    }
}