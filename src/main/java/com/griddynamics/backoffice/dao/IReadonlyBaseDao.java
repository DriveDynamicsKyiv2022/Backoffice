package com.griddynamics.backoffice.dao;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReadonlyBaseDao<T> {
    List<T> findAll(Pageable pageable);

    T find(String id);
}
