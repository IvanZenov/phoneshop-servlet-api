package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> findProducts();
}
