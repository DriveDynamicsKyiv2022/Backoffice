package com.griddynamics.backoffice.controller.user;

import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.order.OrderDto;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/orders")
public class OrderController {
    private final IOrderReadonlyService orderReadonlyService;

    @Autowired
    public OrderController(IOrderReadonlyService orderReadonlyService) {
        this.orderReadonlyService = orderReadonlyService;
    }

    @GetMapping
    public Page<OrderDto> getUserOrders(@RequestBody UserOrderFilteringRequest userOrderRequest, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderReadonlyService.getUserOrders(userOrderRequest, pageable).map(BuildingUtils::getDto);
    }
}
