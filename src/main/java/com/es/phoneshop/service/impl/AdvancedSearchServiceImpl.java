package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.advancedSearch.SearchParams;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.AdvancedSearchService;

import java.util.List;
import java.util.stream.Collectors;

public class AdvancedSearchServiceImpl implements AdvancedSearchService {

    private static volatile AdvancedSearchServiceImpl INSTANCE;

    private AdvancedSearchServiceImpl() {
    }

    public static AdvancedSearchServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AdvancedSearchServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdvancedSearchServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public List<Product> findProductsWithCode(String query) {
        List<Product> products = ArrayListProductDao.getInstance().findProducts();
        if (query != null && !query.isEmpty()) {
            return products.stream()
                    .filter(product -> product.getCode().equals(query))
                    .collect(Collectors.toList());
        } else {
            return products;
        }
    }

    @Override
    public List<Product> findProducts(SearchParams params) {
        List<Product> result = findProductsWithCode(params.getProductCode());

        return result.stream()
                .filter(product -> product.getPrice().compareTo(params.getMinPrice()) >= 0)
                .filter(product -> product.getPrice().compareTo(params.getMaxPrice()) <= 0)
                .filter(product -> product.getStock() >= params.getMinStock())
                .collect(Collectors.toList());
    }
}
