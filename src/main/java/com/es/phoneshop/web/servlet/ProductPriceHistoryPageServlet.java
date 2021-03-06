package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.web.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {

    private static final String PRODUCT_HISTORY_PAGE_JSP = "productPriceHistory";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getPathInfo();
        req.setAttribute("product", ArrayListProductDao.getInstance().getById(Long.valueOf(productId.substring(1))));
        req.getRequestDispatcher(ServletUtil.createViewPath(PRODUCT_HISTORY_PAGE_JSP)).forward(req, resp);
    }
}
