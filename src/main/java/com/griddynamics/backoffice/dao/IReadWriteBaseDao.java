package com.griddynamics.backoffice.dao;

public interface IReadWriteBaseDao<T> extends IReadonlyBaseDao<T> {
    T save(T entity);

    boolean delete(String id);

    T updateEntity(String id, T newEntity);

}
