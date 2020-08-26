package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.advancedSearch.SearchParams;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.AdvancedSearchService;
import com.es.phoneshop.service.impl.AdvancedSearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AdvancedSearchPageServlet extends HttpServlet {
    private AdvancedSearchService searchService;

    @Override
    public void init() throws ServletException {
        searchService = AdvancedSearchServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products",new ArrayList<Product>());

        req.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();

        String minPriceString = req.getParameter("minPrice");
        String maxPriceString = req.getParameter("maxPrice");
        String minStockString = req.getParameter("minStock");

        //So bad practice, only at the end I saw that the lines can be empty
        if (minPriceString == null || minPriceString.isEmpty()) {
            minPriceString = "0";
        }
        if (maxPriceString == null || maxPriceString.isEmpty()) {
            maxPriceString = "10000";
        }
        if (minStockString == null || minStockString.isEmpty()) {
            minStockString = "0";
        }
        String productCode = req.getParameter("code");

        SearchParams searchParams = new SearchParams();
        searchParams.setProductCode(productCode);

        //No have time to put in method
        try {
            searchParams.setMinPrice(new BigDecimal(Integer.parseInt(minPriceString)));
        } catch (NumberFormatException exception) {
            errors.put("minPrice", "Not a number");
        }
        try {
            searchParams.setMaxPrice(new BigDecimal(Integer.parseInt(maxPriceString)));
        } catch (NumberFormatException exception) {
            errors.put("maxPrice", "Not a number");
        }
        try {
            searchParams.setMinStock(Integer.parseInt(minStockString));
        } catch (NumberFormatException exception) {
            errors.put("minStock", "Not a number");
        }

        if (errors.isEmpty()){
            List<Product> products = searchService.findProducts(searchParams);
            req.setAttribute("products",products);
            req.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(req, resp);
        }
        else {
            req.setAttribute("errors", errors);
            doGet(req, resp);
        }
    }

}
