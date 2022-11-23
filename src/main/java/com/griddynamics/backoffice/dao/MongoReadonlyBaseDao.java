package com.griddynamics.backoffice.dao;

import com.griddynamics.backoffice.model.Tariff;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public abstract class MongoReadonlyBaseDao<T> implements IReadonlyBaseDao<T> {
    protected final MongoTemplate mongoTemplate;
    protected final Class<T> entityClass;
    protected final String entityName;

    public MongoReadonlyBaseDao(MongoTemplate mongoTemplate, Class<T> entityClass, String entityName) {
        this.mongoTemplate = mongoTemplate;
        this.entityClass = entityClass;
        this.entityName = entityName;
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public T find(String id) {
        Query query = getSearchByIdQuery(id);
        checkIfExistsOrThrowException(query);
        T result = mongoTemplate.findOne(query, entityClass);
        if (result == null) {
            throw new RuntimeException(String.format("Couldn't fetch %s from database", entityName));
        }
        return result;
    }

    protected void checkIfExistsOrThrowException(Query query) {
        if (!mongoTemplate.exists(query, entityClass)) {
            throw new IllegalArgumentException(String.format("No such %s", entityName));
        }
    }

    @NonNull
    protected Query getSearchByIdQuery(String id) {
        return new Query(Criteria.where(entityName + "Id").is(id));
    }
}
