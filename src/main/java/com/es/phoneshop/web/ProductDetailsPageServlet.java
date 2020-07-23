package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.CartServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = parseProductId(req);
            req.setAttribute("product", productDao.getProduct(id));
            req.setAttribute("cart",cartService.getCart(req));

            req.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(req, resp);

        }
        catch (ProductNotFoundException | NumberFormatException ex) {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = parseProductId(req);
        String quantityString = req.getParameter("quantity");
        Cart cart = cartService.getCart(req);
        int quantity;
        try {
            NumberFormat format = NumberFormat.getInstance(req.getLocale());
            quantity = (Integer) format.parse(quantityString).intValue();
        }
        catch (NumberFormatException | ParseException ex) {
            req.setAttribute("error", "Not a number");
            doGet(req,resp);
            return;
        }

        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            req.setAttribute("error", "Out of stock, max available" + e.getStockAvailable());
            doGet(req,resp);
            return;
        }
        req.setAttribute("operationResult","Product added to cart");
        resp.sendRedirect(req.getContextPath() + "/products/" + productId);

    }


    private Long parseProductId (HttpServletRequest req) {
        String productId = req.getPathInfo().substring(1);
        return Long.valueOf(productId);
    }
}
