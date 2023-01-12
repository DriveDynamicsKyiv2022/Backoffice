package com.griddynamics.backoffice.service.order;

import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.request.ManagerOrderFilteringRequest;
import com.griddynamics.backoffice.model.request.UserOrderFilteringRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderReadonlyService {
    Page<Order> getUserOrders(UserOrderFilteringRequest orderRequest, Pageable pageable);

    Page<Order> getAllOrders(ManagerOrderFilteringRequest orderRequest, Pageable pageable);
}
