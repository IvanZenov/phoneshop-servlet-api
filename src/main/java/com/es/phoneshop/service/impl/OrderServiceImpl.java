package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.OrderDaoImpl;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.ContactCustomerDetail;
import com.es.phoneshop.model.order.DeliveryOrderDetail;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final BigDecimal CONST_DELIVERY_COST = new BigDecimal(5);
    private OrderDao orderDao;

    private static volatile OrderServiceImpl INSTANCE;

    private OrderServiceImpl() {
        orderDao = OrderDaoImpl.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();

        order.setCartItemList(cart.getItems().stream().map(item -> {
            try {
                return (CartItem) item.clone();
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException();
            }
        }).collect(Collectors.toList()));

        DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
        deliveryOrderDetail.setDeliveryCost(CONST_DELIVERY_COST);

        order.setSubtotal(cart.getTotalCost());
        order.setContactCustomerDetail(new ContactCustomerDetail());
        order.setDeliveryOrderDetail(deliveryOrderDetail);
        order.setTotalPrice(cart.getTotalCost().add(order.getDeliveryOrderDetail().getDeliveryCost()));

        return order;
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderDao.getOrder(secureId);
    }


    @Override
    public void placeOrder(Order order) {
        String id = UUID.randomUUID().toString();
        order.setSecureId(id);
        orderDao.save(order);
    }
}
