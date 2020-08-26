package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collection;
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

        Product product = productDao.getById(productId);

        if (product.getStock() < quantity) {
            throw new OutOfStockException(product, quantity, product.getStock());
        }

        Optional<CartItem> productInCart = getOptionalCartItem(cart, productId);

        if (productInCart.isPresent()) {
            CartItem cartItem = productInCart.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }

        //don't control if the user changes their mind
        product.setStock(product.getStock() - quantity);
        recalculateCart(cart);
    }

    @Override
    public void update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getById(productId);

        Optional<CartItem> productInCart = getOptionalCartItem(cart, productId);

        int productInCartQuantity = productInCart.map(CartItem::getQuantity).orElse(0);

        //add method change the product directly, so we return what was in the cart to product
        if (product.getStock() + productInCartQuantity < quantity) {
            throw new OutOfStockException(product, quantity, product.getStock() + productInCartQuantity);
        }

        productInCart.ifPresent(cartItem -> {
            cartItem.setQuantity(quantity);

            product.setStock(product.getStock() + productInCartQuantity - quantity);
            recalculateCart(cart);
        });
    }

    @Override
    public void delete(Cart cart, Long productId) {
        Optional<CartItem> productInCart = getOptionalCartItem(cart, productId);

        productInCart.map(CartItem::getProduct).ifPresent(product -> {
            int productInCartQuantity = productInCart.map(CartItem::getQuantity).orElse(0);

            cart.getItems().removeIf(cartItem -> productId.equals(cartItem.getProduct().getId()));
            product.setStock(product.getStock() + productInCartQuantity);

            recalculateCart(cart);
        });
    }

    @Override
    public void deleteAll(Cart cart) {
        cart.getItems().removeAll(cart.getItems());
        recalculateCart(cart);
    }

    private Optional<CartItem> getOptionalCartItem(Cart cart, Long productId) {
        return cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
    }

    private void recalculateCart(Cart cart) {

        cart.setTotalQuantity(cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());

        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
