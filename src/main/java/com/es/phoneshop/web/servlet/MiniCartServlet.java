package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.impl.CartServiceImpl;
import com.es.phoneshop.web.util.ServletUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    private CartService cartService;
    private static final String MINICART_JSP = "minicart";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cart",cartService.getCart(req));

        req.getRequestDispatcher(ServletUtil.createViewPath(MINICART_JSP)).include(req,resp);
    }
}
