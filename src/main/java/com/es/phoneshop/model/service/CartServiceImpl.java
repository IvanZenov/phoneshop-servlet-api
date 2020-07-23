package com.es.phoneshop.model.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.product.Product;

public class CartServiceImpl implements CartService{

    private Cart cart = new Cart();
    private ProductDao productDao;
    private static volatile CartServiceImpl INSTANCE;
    private CartServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static CartServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (CartServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CartServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Long productId, int quantity) {
        Product product = productDao.getProduct(productId);

        cart.getItems().add(new CartItem(product, quantity));

    }
}
