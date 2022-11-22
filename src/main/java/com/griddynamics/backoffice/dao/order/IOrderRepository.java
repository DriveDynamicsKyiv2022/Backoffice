package com.griddynamics.backoffice.dao.order;

import com.griddynamics.backoffice.model.Order;
import com.griddynamics.backoffice.request.ManagerOrderRequest;
import com.griddynamics.backoffice.request.UserOrderRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface IOrderRepository {
    List<Order> getUserOrdersHistoryPaginated(UserOrderRequest userOrderRequest, Pageable pageable);

    Order addOrder(Order order);

    List<Order> getAllOrdersPaginated(ManagerOrderRequest managerOrderRequest, Pageable pageable);
}
