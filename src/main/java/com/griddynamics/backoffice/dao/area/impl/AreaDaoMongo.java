package com.griddynamics.backoffice.dao.area.impl;

import com.griddynamics.backoffice.dao.ReadWriteBaseDaoMongo;
import com.griddynamics.backoffice.dao.area.IAreaDao;
import com.griddynamics.backoffice.model.impl.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class AreaDaoMongo extends ReadWriteBaseDaoMongo<Area> implements IAreaDao {
    @Autowired
    public AreaDaoMongo(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Area.class, "area");
    }
}


