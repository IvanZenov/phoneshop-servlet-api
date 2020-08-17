package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.RecentlyViewProductService;
import com.es.phoneshop.model.service.impl.CartServiceImpl;
import com.es.phoneshop.model.service.impl.ProductService;
import com.es.phoneshop.model.service.impl.RecentlyViewProductServiceImpl;
import com.es.phoneshop.web.util.ServletUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {

    private ProductService productService;
    private CartService cartService;
    private RecentlyViewProductService viewProductService;
    private static final String PRODUCT_LIST_JSP = "productList";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
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

        request.getRequestDispatcher(ServletUtil.createViewPath(PRODUCT_LIST_JSP))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdString = req.getParameter("productId");
        String quantities = req.getParameter("quantity");

        Map<Long, String> errors = new HashMap<>();

        Long productId = Long.valueOf(productIdString);
        int quantity;
        try {
            NumberFormat format = NumberFormat.getInstance(req.getLocale());
            quantity = format.parse(quantities).intValue();
            Cart cart = cartService.getCart(req);
            cartService.add(cart, productId, quantity);
        } catch (NumberFormatException | ParseException | OutOfStockException ex) {
            ServletUtil.handleError(errors,productId,ex);
        }

        if(errors.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?message=Item successfully added to cart");
        }
        else {
            req.setAttribute("errors", errors);
            doGet(req, resp);
        }
    }
}

