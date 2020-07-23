package com.es.phoneshop.model.service;

import com.es.phoneshop.model.cart.Cart;

public interface CartService {
    Cart getCart();
    void add(Long productId,int quantity);
}
