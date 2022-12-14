package com.griddynamics.backoffice.dao;

import com.griddynamics.backoffice.model.IDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReadonlyBaseDao<T extends IDocument> {
    Page<T> findAll(Pageable pageable);

    List<T> findAll();

    T find(String id);
}
