package com.es.phoneshop.model.product;

import com.es.phoneshop.model.enums.SortFieldWithComparator;
import com.es.phoneshop.model.enums.SortOrder;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(String query, String sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id);
}
