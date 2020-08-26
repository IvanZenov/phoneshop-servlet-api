package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

public interface OrderService {
    Order getOrder(Cart cart);

    Order getOrderBySecureId(String secureId);

    void placeOrder(Order order);

}
