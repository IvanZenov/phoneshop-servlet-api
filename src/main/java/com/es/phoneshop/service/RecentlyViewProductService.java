package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewProductService {

    List<Product> getRecentlyViewProduct(HttpServletRequest request);

    void add(List<Product> recentlyViewedProducts, Long productId);
}
