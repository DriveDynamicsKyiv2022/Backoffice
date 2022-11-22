package com.griddynamics.backoffice.dao.order.impl;

import com.griddynamics.backoffice.dao.order.IOrderRepository;
import com.griddynamics.backoffice.dao.order.IPostgresOrderRepository;
import com.griddynamics.backoffice.dao.tariff.IPostgresTariffRepository;
import com.griddynamics.backoffice.model.Order;
import com.griddynamics.backoffice.request.ManagerOrderRequest;
import com.griddynamics.backoffice.request.UserOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public class PostgresOrderRepository implements IOrderRepository {
    private final IPostgresOrderRepository postgresOrderRepository;

    @Autowired
    public PostgresOrderRepository(IPostgresOrderRepository postgresOrderRepository) {
        this.postgresOrderRepository = postgresOrderRepository;
    }


    @Override
    public List<Order> getUserOrdersHistoryPaginated(UserOrderRequest userOrderRequest, Pageable pageable) {
       /* List<Long> userIds = List.of(userOrderRequest.getUserId());
        return postgresOrderRepository.getOrdersPaginated(userIds, userOrderRequest.getStartDate(), userOrderRequest.getEndDate(),
                userOrderRequest.getCarBodyStyle() == null ? null: userOrderRequest.getCarBodyStyle().name());*/
        return null;
    }

    @Override
    public Order addOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersPaginated(ManagerOrderRequest managerOrderRequest, Pageable pageable) {
        return null;
    }
}
