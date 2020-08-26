package com.es.phoneshop.dao;

public interface Dao<T> {
    void save(T entity);

    T getById(Long id);

    void delete(Long id);
}
