package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.RecentlyViewProductService;
import com.es.phoneshop.model.service.impl.ProductService;
import com.es.phoneshop.model.service.impl.RecentlyViewProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {

    private ProductService productService;
    private RecentlyViewProductService viewProductService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductService.getInstance();
        viewProductService = RecentlyViewProductServiceImpl.getInstance();

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = Optional.ofNullable(request.getParameter("sort")).orElse("DEFAULT").toUpperCase();
        String sortOrder = Optional.ofNullable(request.getParameter("order")).orElse("DEFAULT").toUpperCase();
        List<Product> recentlyView = viewProductService.getRecentlyViewProduct(request);


        request.setAttribute("products", productService
                .sortProductsWithField(productService.findProducts(query),
                        sortField,
                        SortOrder.valueOf(sortOrder)));
        request.setAttribute("viewProducts", recentlyView);

        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
