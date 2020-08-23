package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.Dao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractRealizationDao<T extends BaseEntity> implements Dao<T> {

    List<T> entities = new ArrayList<>();
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void save(T entity) {
        readWriteLock.writeLock().lock();
        ListIterator<T> iterator = entities.listIterator();
        boolean isExist = false;

        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next.getId().equals(entity.getId())) {
                iterator.set(entity);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            entities.add(entity);
        }
        readWriteLock.writeLock().unlock();

    }

    @Override
    public T getById(Long id) {
        return entities.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        readWriteLock.writeLock().lock();
        entities.removeIf(product -> id.equals(product.getId()));
        readWriteLock.writeLock().unlock();
    }
}
