package com.griddynamics.backoffice.dao.tariff;

import com.griddynamics.backoffice.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IPostgresTariffRepository extends JpaRepository<Tariff, Long> {
    @Query(value = "UPDATE tariffs SET name = :name, rate_per_hour = :rate_per_hour, car_body_style = :car_body_style, " +
            "description = :description WHERE id = :id",
            nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateById(@Param("id") long id, @Param("name") String name, @Param("rate_per_hour") double ratePerHour,
                      @Param("car_body_style") String carBodyStyleName, @Param("description") String description);
}
