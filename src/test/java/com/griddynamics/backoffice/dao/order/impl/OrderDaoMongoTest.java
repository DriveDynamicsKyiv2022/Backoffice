package com.griddynamics.backoffice.dao.order.impl;

import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.exception.ResourceNotFoundException;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.util.GeneratingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderDaoMongoTest {

    private OrderDaoMongo orderDaoMongo;
    private final MongoTemplate mongoTemplate = mock(MongoTemplate.class);

    @BeforeEach
    void setUp() {
        orderDaoMongo = new OrderDaoMongo(mongoTemplate);
    }

    @Test
    void testFindAll() {
        List<Order> expectedOrders = List.of(TestingConstants.SAMPLE_ORDER);
        when(mongoTemplate.findAll(Order.class)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderDaoMongo.findAll();

        assertEquals(expectedOrders, actualOrders);
        verify(mongoTemplate).findAll(any());
    }

    @Test
    void testFindAllPaginated() {
        List<Order> expectedOrders = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 3);
        Page<Order> expectedPage = new PageImpl<>(expectedOrders, pageable, expectedOrders.size());
        when(mongoTemplate.count(any(), any(Class.class))).thenReturn((long) expectedOrders.size());
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(expectedOrders);

        Page<Order> actualPage = orderDaoMongo.findAll(pageable);

        assertEquals(expectedPage, actualPage);
        verify(mongoTemplate).count(any(), any(Class.class));
        verify(mongoTemplate).find(any(), any());
    }

    @Test
    void testFindAllPaginated_ShouldFail() {
        List<Order> expectedOrders = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(1, 1);
        when(mongoTemplate.count(any(), any(Class.class))).thenReturn((long) expectedOrders.size());

        assertThrows(PaginationException.class, () -> orderDaoMongo.findAll(pageable));
        verify(mongoTemplate).count(any(), any(Class.class));
        verify(mongoTemplate, never()).find(any(), any());
    }

    @Test
    void testFind() {
        Order expectedOrder = TestingConstants.SAMPLE_ORDER;
        String id = expectedOrder.getEntityId();
        when(mongoTemplate.findOne(any(), any(Class.class))).thenReturn(expectedOrder);
        when(mongoTemplate.exists(any(), any(Class.class))).thenReturn(true);

        Order actualOrder = orderDaoMongo.find(id);

        assertEquals(expectedOrder, actualOrder);
        verify(mongoTemplate).findOne(any(), any(Class.class));
        verify(mongoTemplate).exists(any(), any(Class.class));
    }

    @Test
    void testFindNonExistent() {
        String id = GeneratingUtils.generateId();
        when(mongoTemplate.exists(any(), any(Class.class))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderDaoMongo.find(id));
        verify(mongoTemplate).exists(any(), any(Class.class));
        verify(mongoTemplate, never()).findOne(any(), any());
    }

    @Test
    void testFind_ShouldFail() {
        Order expectedOrder = TestingConstants.SAMPLE_ORDER;
        String id = expectedOrder.getEntityId();
        when(mongoTemplate.findOne(any(), any(Class.class))).thenReturn(null);
        when(mongoTemplate.exists(any(), any(Class.class))).thenReturn(true);

        assertThrows(DatabaseException.class, () -> orderDaoMongo.find(id));
        verify(mongoTemplate).findOne(any(), any(Class.class));
        verify(mongoTemplate).exists(any(), any(Class.class));
    }


    @Test
    void getUserOrdersHistoryPaginated() {
    }

    @Test
    void getAllOrdersPaginated() {
    }

}