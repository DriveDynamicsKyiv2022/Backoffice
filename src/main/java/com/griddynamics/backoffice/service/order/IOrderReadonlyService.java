package com.griddynamics.backoffice.service.order;

import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderReadonlyService {
    Page<Order> getUserOrders(UserOrderFilteringRequest orderRequest, Pageable pageable);

    Page<Order> getAllOrders(ManagerOrderFilteringRequest orderRequest, Pageable pageable);
}
