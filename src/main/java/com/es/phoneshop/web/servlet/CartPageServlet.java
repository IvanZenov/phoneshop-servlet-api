package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.impl.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = CartServiceImpl.getInstance();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productIds = req.getParameterValues("productId");
        String[] quantities = req.getParameterValues("quantity");

        Map<Long,String> errors = new HashMap<>();

        for (int i = 0; i <productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);

            int quantity;
            try {
                NumberFormat format = NumberFormat.getInstance(req.getLocale());
                quantity = format.parse(quantities[i]).intValue();
                Cart cart = cartService.getCart(req);
                cartService.update(cart, productId, quantity);

            }
            catch (NumberFormatException | ParseException | OutOfStockException ex) {
                handleError(errors,productId,ex);
            }
        }
        if (errors.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?message=Cart updated successfully");
        }
        else {
            req.setAttribute("errors",errors);
            doGet(req,resp);
        }
    }
    //add this method, because problem with initialization quantity
    private void handleError(Map<Long, String> errors, Long productId, Exception ex) {
        if (ex.getClass().equals(ParseException.class)||ex.getClass().equals(NumberFormatException.class)) {
            errors.put(productId,"Not a number");
        }
        if (ex.getClass().equals(OutOfStockException.class)) {
            errors.put(productId,"Out of stock, max available " + ((OutOfStockException) ex).getStockAvailable());
        }
    }


}