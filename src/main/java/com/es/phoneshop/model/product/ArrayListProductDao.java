package com.es.phoneshop.model.product;

import com.es.phoneshop.model.enums.SortFieldWithComparator;
import com.es.phoneshop.model.enums.SortOrder;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();;
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static volatile ArrayListProductDao INSTANCE;

    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ArrayListProductDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ArrayListProductDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return products
                .stream()
                .filter(product -> id.equals(product.getId()))
                .findAny();
    }

    @Override
    public List<Product> findProducts(String query, String sortField, SortOrder sortOrder) {
        //TODO: write ranking logic
        Stream<Product> productStream = products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> query == null || query.isEmpty() || product.getDescription().contains(query))
                .filter(this::productIsInStock);

        //Default no sorting
        if (sortField.equals("DEFAULT") && SortOrder.DEFAULT == sortOrder){
            return productStream
                    .collect(Collectors.toList());
        }
        else {
            return productStream
                    .sorted(SortFieldWithComparator.sortBy(sortField,sortOrder))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void save(Product product) {
        readWriteLock.writeLock().lock();
        ListIterator<Product> iterator = products.listIterator();
        boolean isExist = false;

        while (iterator.hasNext()) {
            Product next = iterator.next();
            if (next.getId().equals(product.getId())) {
                iterator.set(product);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            products.add(product);
        }
        readWriteLock.writeLock().unlock();
    }

    @Override
    public void delete(Long id) {
        readWriteLock.writeLock().lock();
        products.removeIf(product -> id.equals(product.getId()));
        readWriteLock.writeLock().unlock();
    }

    private boolean productIsInStock(Product product){
        return product.getStock()>0;
    }
}
