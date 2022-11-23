package com.griddynamics.backoffice.dao.tariff.impl;

import com.griddynamics.backoffice.dao.MongoReadWriteBaseDao;
import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoTariffDao extends MongoReadWriteBaseDao<Tariff> implements ITariffDao {
    @Autowired
    public MongoTariffDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Tariff.class, "tariff");
    }
}
