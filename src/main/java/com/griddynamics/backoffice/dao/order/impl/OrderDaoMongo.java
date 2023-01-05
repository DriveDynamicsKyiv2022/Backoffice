package com.griddynamics.backoffice.dao.order.impl;

import com.griddynamics.backoffice.dao.ReadonlyBaseDaoMongo;
import com.griddynamics.backoffice.dao.order.IOrderDao;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.model.impl.Order;
import com.griddynamics.backoffice.util.CollectionUtils;
import com.griddynamics.backoffice.util.PaginationUtils;
import com.griddynamics.request.AbstractOrderFilteringRequest;
import com.griddynamics.request.ManagerOrderFilteringRequest;
import com.griddynamics.request.UserOrderFilteringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("local")
public class OrderDaoMongo extends ReadonlyBaseDaoMongo<Order> implements IOrderDao {
    @Autowired
    public OrderDaoMongo(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Order.class, "order");
    }

    @Override
    public Page<Order> getUserOrdersHistoryPaginated(UserOrderFilteringRequest userOrderRequest, Pageable pageable) {
        Query query = new Query(Criteria.where("userId").is(userOrderRequest.getUserId()));
        Criteria criteria = configureCarAndDateCriteria(userOrderRequest);
        query.addCriteria(criteria);
        long totalCount = getTotalCount(query);
        if(!PaginationUtils.isValidPage(totalCount, pageable)) {
            throw new PaginationException("No such page");
        }
        query.with(pageable);
        List<Order> orders = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(orders, pageable, totalCount);
    }

    @Override
    public Page<Order> getAllOrdersPaginated(ManagerOrderFilteringRequest managerOrderRequest, Pageable pageable) {
        Query query = new Query();
        if (!CollectionUtils.isEmpty(managerOrderRequest.getUserIds())) {
            query.addCriteria(Criteria.where("userId").in(managerOrderRequest.getUserIds()));
        }
        Criteria carAndDateCriteria = configureCarAndDateCriteria(managerOrderRequest);
        query.addCriteria(carAndDateCriteria);
        long totalCount = getTotalCount(query);
        if(!PaginationUtils.isValidPage(totalCount, pageable)) {
            throw new PaginationException("No such page");
        }
        query.with(pageable);
        List<Order> orders = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(orders, pageable, totalCount);
    }

    protected Criteria configureCarAndDateCriteria(AbstractOrderFilteringRequest orderRequest) {
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
