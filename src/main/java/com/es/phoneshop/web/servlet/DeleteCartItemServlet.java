package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdString = req.getPathInfo().substring(1);
        Long productId = Long.valueOf(productIdString);

        Cart cart = cartService.getCart(req);
        cartService.delete(cart,productId);

        resp.sendRedirect(req.getContextPath() + "/cart?message=Cart item removed successfully");
    }
}
