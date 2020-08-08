package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private ProductDao productDao;
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";

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
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws OutOfStockException {

        Product product = productDao.getProduct(productId);

        if (product.getStock() < quantity) {
            throw new OutOfStockException(product,quantity,product.getStock());
        }

        Optional<CartItem> productInCart = getOptionalCartItem(cart, productId);

        if (productInCart.isPresent()) {
            CartItem cartItem = productInCart.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        else {
            cart.getItems().add(new CartItem(product,quantity));
        }

        //don't control if the user changes their mind
        product.setStock(product.getStock() - quantity);
    }

    @Override
    public void update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);

        Optional<CartItem> productInCart = getOptionalCartItem(cart,productId);

        int productInCartQuantity = productInCart.map(CartItem::getQuantity).orElse(0);

        //add method change the product directly, so we return what was in the cart to product
        if (product.getStock() + productInCartQuantity < quantity) {
            throw new OutOfStockException(product,quantity,product.getStock() + productInCartQuantity);
        }

        CartItem cartItem = productInCart.get();
        cartItem.setQuantity(quantity);

        product.setStock(product.getStock() + productInCartQuantity - quantity);
    }

    @Override
    public void delete(Cart cart, Long productId) {
        Optional<CartItem> productInCart = getOptionalCartItem(cart, productId);
        Product product = productInCart.get().getProduct();

        int productInCartQuantity = productInCart.map(CartItem::getQuantity).orElse(0);

        cart.getItems().removeIf(cartItem -> productId.equals(cartItem.getProduct().getId()));
        product.setStock(product.getStock() + productInCartQuantity);
    }

    private Optional<CartItem> getOptionalCartItem (Cart cart, Long productId) {
        return cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
    }
}
