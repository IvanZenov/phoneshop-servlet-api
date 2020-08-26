package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao extends AbstractRealizationDao<Product> implements ProductDao {


    private static volatile ArrayListProductDao INSTANCE;

    private ArrayListProductDao() {
        super.entities = new ArrayList<>();
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
    public List<Product> findProducts() {
        return entities.stream()
                .filter(product -> product.getPrice() != null)
                .filter(this::productIsInStock)
                .collect(Collectors.toList());
    }


    private boolean productIsInStock(Product product) {
        return product.getStock() > 0;
    }

    public void setProducts(List<Product> products) {
        this.entities = products;
    }

}
