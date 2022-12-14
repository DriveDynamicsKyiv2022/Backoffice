package com.griddynamics.backoffice.dao.tariff.impl;

import com.griddynamics.backoffice.dao.ReadWriteBaseDaoMongo;
import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.model.impl.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class TariffDaoMongo extends ReadWriteBaseDaoMongo<Tariff> implements ITariffDao {
    @Autowired
    public TariffDaoMongo(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Tariff.class, "tariff");
    }
}
