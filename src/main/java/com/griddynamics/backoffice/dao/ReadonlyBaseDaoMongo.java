package com.griddynamics.backoffice.dao;

import com.griddynamics.backoffice.model.IDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class ReadonlyBaseDaoMongo<T extends IDocument> implements IReadonlyBaseDao<T> {
    protected final MongoTemplate mongoTemplate;
    protected final Class<T> entityClass;
    protected final String entityName;

    public ReadonlyBaseDaoMongo(MongoTemplate mongoTemplate, Class<T> entityClass, String entityName) {
        this.mongoTemplate = mongoTemplate;
        this.entityClass = entityClass;
        this.entityName = entityName;
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Query query = new Query();
        long totalCount = getTotalCount(query);
        query.with(pageable);
        List<T> entities = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(entities, pageable, totalCount);
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

    public long getTotalCount(Query query) {
        return mongoTemplate.count(query, entityClass);
    }

    protected void checkIfExistsOrThrowException(Query query) {
        if (!mongoTemplate.exists(query, entityClass)) {
            throw new IllegalArgumentException(String.format("No such %s", entityName));
        }
    }

    @NotNull
    protected Query getSearchByIdQuery(String id) {
        return new Query(Criteria.where(entityName + "Id").is(id));
    }
}
