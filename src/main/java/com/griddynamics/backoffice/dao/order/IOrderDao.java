package com.griddynamics.backoffice.dao.order;

import com.griddynamics.backoffice.dao.IReadonlyBaseDao;
import com.griddynamics.backoffice.model.Order;
import com.griddynamics.backoffice.request.ManagerOrderRequest;
import com.griddynamics.backoffice.request.UserOrderRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IOrderDao extends IReadonlyBaseDao<Order> {
    List<Order> getUserOrdersHistoryPaginated(UserOrderRequest userOrderRequest, Pageable pageable);

    List<Order> getAllOrdersPaginated(ManagerOrderRequest managerOrderRequest, Pageable pageable);
}
