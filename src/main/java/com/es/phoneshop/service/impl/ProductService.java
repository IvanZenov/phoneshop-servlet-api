package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.enums.SortFieldWithComparator;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ProductService {
    private static volatile ProductService INSTANCE;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductService();
                }
            }
        }
        return INSTANCE;
    }

    public List<Product> findProducts(String query) {
        List<Product> products = ArrayListProductDao.getInstance().findProducts();
        if (query != null && !query.isEmpty()) {
            return products.stream()
                    .filter(product -> Arrays.stream(query.split(" "))
                            .allMatch(word ->
                                    Arrays.stream(product.getDescription().split(" ")).anyMatch(desc -> desc.contains(word))))
                    .sorted(Comparator.comparing(product -> numberOfMatch(query, product.getDescription()), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } else {
            return products;
        }
    }

    public List<Product> sortProductsWithField(List<Product> products, String sortField, SortOrder sortOrder) {
        if (sortField.equals("DEFAULT") && SortOrder.DEFAULT == sortOrder) {
            return products;
        } else {
            return products
                    .stream()
                    .sorted(SortFieldWithComparator.sortBy(sortField, sortOrder))
                    .collect(Collectors.toList());
        }
    }

    private double numberOfMatch(String query, String desc) {
        return query.split(" ").length / desc.split(" ").length;
    }

}
