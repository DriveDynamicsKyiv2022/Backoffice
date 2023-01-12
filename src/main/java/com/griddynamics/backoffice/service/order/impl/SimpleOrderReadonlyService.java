package com.griddynamics.backoffice.service.order.impl;

import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.model.request.ManagerOrderFilteringRequest;
import com.griddynamics.backoffice.model.request.UserOrderFilteringRequest;
import com.griddynamics.backoffice.service.order.IOrderReadonlyService;
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
        if (orderRequest.getEndDate() == null && orderRequest.getStartDate() == null &&
                orderRequest.getCarBodyStyles() == null && orderRequest.getUserIds() == null) {
            return orderDao.findAll(pageable);
        }
        return orderDao.getAllOrdersPaginated(orderRequest, pageable);
    }
}
