package com.griddynamics.backoffice.dao.order;

import com.griddynamics.backoffice.model.Order;
import com.griddynamics.carservice.enums.CarBodyStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IPostgresOrderRepository extends JpaRepository<Order, Long> {
/*
    @Query(value = "SELECT * FROM orders WHERE user_id = ANY(:user_ids) AND (start_date <= :start_date OR :startDate IS NULL)" +
            "AND (end_date >= :end_date OR :end_date IS NULL) AND (car_body_style = :car_body_style OR :car_body_style IS NULL)")
*/
    /*List<Order> getOrdersPaginated(@Param("user_ids") List<Long> userIds, @Param("start_date") Date startDate,
                                   @Param("end_date") Date endDate, @Param("car_body_style") String carBodyStyle);*/
}
