package com.es.phoneshop.model.product;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.es.phoneshop.model.product.ProductInitializer.createProducts;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = createProducts();
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static volatile ArrayListProductDao INSTANCE;

    private ArrayListProductDao() {}

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
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice()!=null)
                .filter(this::productIsInStock)
                .collect(Collectors.toList());
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
