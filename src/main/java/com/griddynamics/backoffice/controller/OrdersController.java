package com.griddynamics.backoffice.controller;

import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.model.request.ManagerOrderFilteringRequest;
import com.griddynamics.backoffice.model.request.UserOrderFilteringRequest;
import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.RestUtils;
import com.griddynamics.backoffice.util.VariablesUtils;
import com.griddynamics.car.enums.CarBodyStyle;
import com.griddynamics.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping
public class OrdersController {
    private final IOrderReadonlyService orderReadonlyService;

    @Autowired
    public OrdersController(IOrderReadonlyService orderReadonlyService) {
        this.orderReadonlyService = orderReadonlyService;
    }

    @GetMapping("orders")
    public ResponseEntity<Page<OrderDto>> getOrdersHistory(Integer pageNumber, Integer pageSize,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                           @RequestParam Set<CarBodyStyle> carBodyStyles,
                                                           @RequestParam Set<Long> userIds,
                                                           UriComponentsBuilder uriComponentsBuilder) {
        if (VariablesUtils.notAllSpecified(pageNumber, pageSize)) {
            throw new PaginationException("Page parameters must be specified");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        ManagerOrderFilteringRequest orderFilteringRequest = ManagerOrderFilteringRequest.builder()
                .userIds(userIds == null || userIds.size() == 0 ? null : userIds)
                .carBodyStyles(carBodyStyles)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Page<OrderDto> orderDtos = orderReadonlyService.getAllOrders(orderFilteringRequest, pageable).map(BuildingUtils::getDto);
        MultiValueMap<String, String> headers = RestUtils.configureHttpHeadersForPage(orderDtos, uriComponentsBuilder, "orders");
        return new ResponseEntity<>(orderDtos, headers, HttpStatus.OK);
    }

    @GetMapping("users/{userId}/orders")
    public ResponseEntity<Page<OrderDto>> getUserOrdersHistory(Integer pageNumber, Integer pageSize,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                               @RequestParam Set<CarBodyStyle> carBodyStyles,
                                                               @PathVariable(name = "userId") long userId,
                                                               UriComponentsBuilder uriComponentsBuilder) {
        if (VariablesUtils.notAllSpecified(pageNumber, pageSize)) {
            throw new PaginationException("Page parameters must be specified");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UserOrderFilteringRequest orderFilteringRequest = UserOrderFilteringRequest.builder()
                .userId(userId)
                .carBodyStyles(carBodyStyles)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Page<OrderDto> orderDtos = orderReadonlyService.getUserOrders(orderFilteringRequest, pageable).map(BuildingUtils::getDto);
        MultiValueMap<String, String> headers = RestUtils.configureHttpHeadersForPage(orderDtos, uriComponentsBuilder, "orders");
        return new ResponseEntity<>(orderDtos, headers, HttpStatus.OK);
    }
}
