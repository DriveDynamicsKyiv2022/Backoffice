package com.griddynamics.backoffice.controller;

import com.griddynamics.backoffice.TestingConstants;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.order.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrdersControllerTest {
    private OrdersController ordersController;
    private final IOrderReadonlyService orderReadonlyService = mock(IOrderReadonlyService.class);
    private final UriComponentsBuilder uriComponentsBuilder = mock(UriComponentsBuilder.class);
    private final UriComponents uriComponents = mock(UriComponents.class);

    @BeforeEach
    void setUp() {
        ordersController = new OrdersController(orderReadonlyService);
    }

    @Test
    void getOrdersHistory() {
        List<Order> content = List.of(TestingConstants.SAMPLE_ORDER);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Order> expectedPage = new PageImpl<>(content, pageable, 4);
        when(orderReadonlyService.getAllOrders(any(), any())).thenReturn(expectedPage);
        when(uriComponentsBuilder.pathSegment(any())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.cloneBuilder()).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), any(Object.class))).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        when(uriComponents.toUriString()).thenReturn(null);

        var ordersHistory = ordersController
                .getOrdersHistory(0, 2, null, null, null, uriComponentsBuilder);

        var orderDtoPage = ordersHistory.getBody();

        List<OrderDto> expected = expectedPage.getContent().stream().map(BuildingUtils::getDto).collect(Collectors.toList());
    }

    @Test
    void getUserOrdersHistory() {
    }
}