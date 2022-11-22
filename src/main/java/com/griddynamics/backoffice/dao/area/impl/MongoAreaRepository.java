package com.griddynamics.backoffice.dao.area.impl;

import com.griddynamics.backoffice.dao.area.IAreaRepository;
import com.griddynamics.backoffice.model.Area;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoAreaRepository implements IAreaRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoAreaRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Area addArea(Area area) {
        return mongoTemplate.insert(area);
    }

    @Override
    public Area getArea(String id) {
        Query query = getSearchByIdQuery(id);
        return mongoTemplate.findOne(query, Area.class);
    }

    @Override
    public Area updateArea(String id, Area newArea) {
        Query query = getSearchByIdQuery(id);
        FindAndReplaceOptions options = new FindAndReplaceOptions().returnNew();
        return mongoTemplate.findAndReplace(query, newArea, options);
    }

    @Override
    public boolean deleteArea(String id) {
        getSearchByIdQuery(id);
        return false;
    }

    @Override
    public List<Area> getAreas(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        return mongoTemplate.find(query, Area.class);
    }

    @NonNull
    private static Query getSearchByIdQuery(String id) {
        return new Query(Criteria.where("areaId").is(id));
    }
}
