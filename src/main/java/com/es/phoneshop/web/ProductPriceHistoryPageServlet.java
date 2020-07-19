package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getPathInfo();
        req.setAttribute("product", ArrayListProductDao.getInstance().getProduct(Long.valueOf(productId.substring(1))).get());
        req.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(req, resp);
    }
}
