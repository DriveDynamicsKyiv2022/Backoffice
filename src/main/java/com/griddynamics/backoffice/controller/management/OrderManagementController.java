package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.order.OrderDto;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderManagementController {
    private final IOrderReadonlyService orderReadonlyService;

    @Autowired
    public OrderManagementController(IOrderReadonlyService orderReadonlyService) {
        this.orderReadonlyService = orderReadonlyService;
    }

    @GetMapping
    public Page<OrderDto> getOrdersHistory(@RequestBody ManagerOrderFilteringRequest managerOrderRequest, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderReadonlyService.getAllOrders(managerOrderRequest, pageable).map(BuildingUtils::getDto);
    }
}
