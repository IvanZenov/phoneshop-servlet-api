package com.es.phoneshop.web;

import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = Optional.ofNullable(request.getParameter("sort")).orElse("DEFAULT").toUpperCase();
        String sortOrder = Optional.ofNullable(request.getParameter("order")).orElse("DEFAULT").toUpperCase();

        request.setAttribute("products", ProductService
                .getInstance()
                .sortProductsWithField(ProductService.getInstance().findProducts(query),
                        sortField,
                        SortOrder.valueOf(sortOrder)));

        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
