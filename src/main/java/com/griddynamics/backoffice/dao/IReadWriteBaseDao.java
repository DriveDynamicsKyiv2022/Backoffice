package com.griddynamics.backoffice.dao;

import com.griddynamics.backoffice.model.IDocument;

public interface IReadWriteBaseDao<T extends IDocument> extends IReadonlyBaseDao<T> {
    T save(T entity);

    boolean delete(String id);

    T updateEntity(T newEntity);

}
