package com.griddynamics.backoffice.service.order.impl;

import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SimpleOrderReadonlyService implements IOrderReadonlyService {
    private final IOrderDao orderDao;

    @Autowired
    public SimpleOrderReadonlyService(IOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Page<Order> getUserOrders(UserOrderFilteringRequest orderRequest, Pageable pageable) {
        return orderDao.getUserOrdersHistoryPaginated(orderRequest, pageable);
    }

    @Override
    public Page<Order> getAllOrders(ManagerOrderFilteringRequest orderRequest, Pageable pageable) {
        return orderDao.getAllOrdersPaginated(orderRequest, pageable);
    }
}
