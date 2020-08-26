package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;

public class OrderDaoImpl extends AbstractRealizationDao<Order> implements OrderDao {

    private static volatile OrderDaoImpl INSTANCE;

    private OrderDaoImpl() {
        super.entities = new ArrayList<>();
    }

    public static OrderDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderDaoImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Order getOrder(String secureId) throws ProductNotFoundException {
        return super.entities.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with secureId" + secureId + "not found"));
    }
}
