package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ContactCustomerDetail;
import com.es.phoneshop.model.order.DeliveryOrderDetail;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentType;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import com.es.phoneshop.web.util.ServletUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {

    private static final String CHECKOUT_PAGE = "checkout";
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = OrderServiceImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        Order order = orderService.getOrder(cart);

        req.setAttribute("order", order);
        req.getRequestDispatcher(ServletUtil.createViewPath(CHECKOUT_PAGE))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        Cart cart = cartService.getCart(req);
        Order order = orderService.getOrder(cart);

        DeliveryOrderDetail deliveryOrderDetail = order.getDeliveryOrderDetail();
        ContactCustomerDetail contactCustomerDetail = order.getContactCustomerDetail();

        setRequiredParameter(req, "firstName", errors, contactCustomerDetail::setFirstName);
        setRequiredParameter(req, "lastName", errors, contactCustomerDetail::setLastName);
        setRequiredParameter(req, "phone", errors, contactCustomerDetail::setPhone);
        setRequiredParameter(req, "deliveryAddress", errors, contactCustomerDetail::setDeliveryAddress);

        setRequiredParameter(req, "date", errors, deliveryOrderDetail::setDeliveryDate);
        setPaymentMethod(req, errors, order);

        if (errors.isEmpty()) {
            orderService.placeOrder(order);
            cartService.deleteAll(cart);
            resp.sendRedirect(req.getContextPath() + "/overview/" + order.getSecureId());
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("order", order);
            req.getRequestDispatcher(ServletUtil.createViewPath(CHECKOUT_PAGE))
                    .forward(req, resp);
        }
    }

    private void setRequiredParameter(HttpServletRequest req, String param, Map<String, String> errors,
                                      Consumer<String> consumer) {
        String value = req.getParameter(param);
        if (value == null || value.isEmpty()) {
            errors.put(param, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String value = request.getParameter("paymentMethod");
        if (value == null || value.isEmpty()) {
            errors.put("paymentMethod", "Value is required");
        } else {
            order.setPaymentType(PaymentType.valueOf(value));
        }
    }
}
