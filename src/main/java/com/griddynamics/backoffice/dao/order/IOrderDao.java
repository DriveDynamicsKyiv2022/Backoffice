package com.griddynamics.backoffice.dao.order;

import com.griddynamics.backoffice.dao.IReadonlyBaseDao;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderDao extends IReadonlyBaseDao<Order> {
    Page<Order> getUserOrdersHistoryPaginated(UserOrderFilteringRequest userOrderRequest, Pageable pageable);

    Page<Order> getAllOrdersPaginated(ManagerOrderFilteringRequest managerOrderRequest, Pageable pageable);
}
