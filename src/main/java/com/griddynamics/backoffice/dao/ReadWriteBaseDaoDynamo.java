package com.griddynamics.backoffice.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.model.IDocument;

public abstract class ReadWriteBaseDaoDynamo<T extends IDocument> extends ReadonlyBaseDaoDynamo<T> implements IReadWriteBaseDao<T> {
    public ReadWriteBaseDaoDynamo(DynamoDBMapper dynamoDBMapper, Class<T> entityClass, String entityName,
                                  ObjectMapper objectMapper, DynamoDB dynamoDB) {
        super(dynamoDBMapper, entityClass, entityName, objectMapper, dynamoDB);
    }

    @Override
    public T save(T entity) {
        dynamoDBMapper.save(entity);
        return dynamoDBMapper.load(entity);
    }

    @Override
    public boolean delete(String id) {
        T entity = find(id);
        if (entity == null) {
            return false;
        }
        dynamoDBMapper.delete(entity);
        return true;
    }
}
