package com.griddynamics.backoffice.dao.order.impl;

import com.griddynamics.backoffice.dao.MongoReadonlyBaseDao;
import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.model.Order;
import com.griddynamics.backoffice.request.AbstractOrderRequest;
import com.griddynamics.backoffice.request.ManagerOrderRequest;
import com.griddynamics.backoffice.request.UserOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoOrderDao extends MongoReadonlyBaseDao<Order> implements IOrderDao {
    @Autowired
    public MongoOrderDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Order.class, "order");
    }

    @Override
    public List<Order> getUserOrdersHistoryPaginated(UserOrderRequest userOrderRequest, Pageable pageable) {
        Query query = new Query(Criteria.where("userId").is(userOrderRequest.getUserId()));
        Criteria criteria = configureCarAndDateCriteria(userOrderRequest);
        query.with(pageable).addCriteria(criteria);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public List<Order> getAllOrdersPaginated(ManagerOrderRequest managerOrderRequest, Pageable pageable) {
        Query query = new Query();
        if (managerOrderRequest.getUserIds() != null) {
            query.addCriteria(Criteria.where("userId").in(managerOrderRequest.getUserIds()));
        }
        Criteria carAndDateCriteria = configureCarAndDateCriteria(managerOrderRequest);
        query.addCriteria(carAndDateCriteria).with(pageable);
        return mongoTemplate.find(query, entityClass);
    }

    protected Criteria configureCarAndDateCriteria(AbstractOrderRequest orderRequest) {
        Criteria criteria = new Criteria();
        if (orderRequest.getCarBodyStyle() != null) {
            criteria = criteria.and("carBodyStyle").is(orderRequest.getCarBodyStyle());
        }
        if (orderRequest.getStartDate() != null) {
            criteria = criteria.and("startDate").gte(orderRequest.getStartDate());
        }
        if (orderRequest.getEndDate() != null) {
            criteria = criteria.and("endDate").lte(orderRequest.getEndDate());
        }
        return criteria;
    }
}
