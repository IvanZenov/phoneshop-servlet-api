package com.es.phoneshop.model.service;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface RecentlyViewProductService {

    List<Product> getRecentlyViewProduct(HttpServletRequest request);
    void add(List<Product> recentlyViewedProducts, Long productId);


}
