package com.es.phoneshop.model.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.exceptions.OutOfStockException;

public interface CartService {
    Cart getCart();
    void add(Long productId,int quantity) throws OutOfStockException;
}
