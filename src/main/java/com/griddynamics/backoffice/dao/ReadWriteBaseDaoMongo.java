package com.griddynamics.backoffice.dao;

import com.griddynamics.backoffice.model.IDocument;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public abstract class ReadWriteBaseDaoMongo<T extends IDocument> extends ReadonlyBaseDaoMongo<T> implements IReadWriteBaseDao<T> {
    public ReadWriteBaseDaoMongo(MongoTemplate mongoTemplate, Class<T> entityClass, String entityName) {
        super(mongoTemplate, entityClass, entityName);
    }

    @Override
    public T save(T entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(String.format("%s not specified", entityName));
            }
            return mongoTemplate.insert(entity);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Couldn't add %s to database", entityName));
        }
    }

    @Override
    public boolean delete(String id) {
        Query query = getSearchByIdQuery(id);
        return mongoTemplate.remove(query, entityClass).wasAcknowledged();
    }

    @Override
    public T updateEntity(T newEntity) {
        Query query = getSearchByIdQuery(newEntity.getEntityId());
        checkIfExistsOrThrowException(query);
        FindAndReplaceOptions options = new FindAndReplaceOptions().returnNew();
        T result = mongoTemplate.findAndReplace(query, newEntity, options);
        if (result == null) {
            throw new RuntimeException(String.format("Couldn't update %s", entityName));
        }
        return result;
    }
}
