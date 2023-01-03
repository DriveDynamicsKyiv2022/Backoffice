package com.griddynamics.backoffice.service.order.impl;

import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimpleOrderReadonlyServiceTest {
    private final IOrderDao orderDao = mock(IOrderDao.class);
    private SimpleOrderReadonlyService service;

    @BeforeEach
    void setUp() {
        service = new SimpleOrderReadonlyService(orderDao);
    }

    @Test
    void testGetUserOrders() {
        List<Order> content = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Order> expectedPage = new PageImpl<>(content, pageable, 4);
        UserOrderFilteringRequest userOrderFilteringRequest = UserOrderFilteringRequest.builder()
                .userId(1)
                .build();
        when(orderDao.getUserOrdersHistoryPaginated(userOrderFilteringRequest, pageable)).thenReturn(new PageImpl<>(content, pageable, 4));

        Page<Order> userOrders = service.getUserOrders(userOrderFilteringRequest, pageable);

        verify(orderDao).getUserOrdersHistoryPaginated(userOrderFilteringRequest, pageable);
        assertEquals(expectedPage, userOrders);
    }

    @Test
    void testGetAllOrdersWithRequest() {
        List<Order> content = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Order> expectedPage = new PageImpl<>(content, pageable, 4);
        ManagerOrderFilteringRequest managerOrderFilteringRequest = ManagerOrderFilteringRequest.builder()
                .userIds(List.of(1L))
                .build();
        when(orderDao.getAllOrdersPaginated(managerOrderFilteringRequest, pageable)).thenReturn(new PageImpl<>(content, pageable, 4));

        Page<Order> userOrders = service.getAllOrders(managerOrderFilteringRequest, pageable);

        verify(orderDao).getAllOrdersPaginated(managerOrderFilteringRequest, pageable);
        assertEquals(expectedPage, userOrders);
    }

    @Test
    void testGetAllOrdersEmptyRequest() {
        List<Order> content = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Order> expectedPage = new PageImpl<>(content, pageable, 3);
        ManagerOrderFilteringRequest managerOrderFilteringRequest = ManagerOrderFilteringRequest.builder().build();
        when(orderDao.findAll(pageable)).thenReturn(new PageImpl<>(content, pageable, 3));

        Page<Order> userOrders = service.getAllOrders(managerOrderFilteringRequest, pageable);

        verify(orderDao).findAll(pageable);
        verify(orderDao, never()).getAllOrdersPaginated(managerOrderFilteringRequest, pageable);
        assertEquals(expectedPage, userOrders);
    }
}