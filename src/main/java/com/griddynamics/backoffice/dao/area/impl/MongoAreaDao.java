package com.griddynamics.backoffice.dao.area.impl;

import com.griddynamics.backoffice.dao.MongoReadWriteBaseDao;
import com.griddynamics.backoffice.dao.area.IAreaDao;
import com.griddynamics.backoffice.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoAreaDao extends MongoReadWriteBaseDao<Area> implements IAreaDao {
    @Autowired
    public MongoAreaDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Area.class, "area");
    }
}


