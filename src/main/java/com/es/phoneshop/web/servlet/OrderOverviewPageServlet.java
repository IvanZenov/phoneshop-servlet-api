package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import com.es.phoneshop.web.util.ServletUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    private static final String OVERVIEW_JSP = "overview";
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getPathInfo().substring(1);

        Order order = orderService.getOrderBySecureId(orderId);

        req.setAttribute("order", order);
        req.getRequestDispatcher(ServletUtil.createViewPath(OVERVIEW_JSP))
                .forward(req, resp);
    }

}
