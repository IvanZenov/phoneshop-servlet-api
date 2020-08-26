<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Product List">

    <p>
        Welcome to Expert-Soft training!
    </p>

    <table>
        <thead>
        <tr>
            <td>Image</td>

            <td>
                Description
            </td>

            <td class="price">
                Quantity
            </td>

            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.cartItemList}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src="${item.product.imageUrl}">
                </td>

                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                            ${item.product.description}
                    </a>
                </td>

                <td class="price">
                    <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                        ${item.quantity}
                </td>

                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/productPriceHistory/${item.product.id}">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td>Subtotal:</td>
            <td>
                <fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="${not empty order.cartItemList ? order.cartItemList.get(0).product.currency : 0}"/>
            </td>
        </tr>

        <tr>
            <td></td>
            <td></td>
            <td>Delivery cost:</td>
            <td>
                <fmt:formatNumber value="${order.deliveryOrderDetail.deliveryCost}" type="currency"
                                  currencySymbol="${not empty order.cartItemList ? order.cartItemList.get(0).product.currency : 0}"/>
            </td>
        </tr>

        <tr>
            <td></td>
            <td></td>
            <td>Total cost:</td>
            <td>
                <fmt:formatNumber value="${order.totalPrice}" type="currency"
                                  currencySymbol="${not empty order.cartItemList ? order.cartItemList.get(0).product.currency : 0}"/>
            </td>
        </tr>
    </table>

    <h2>Order information: </h2>
    <table>
        <tags:overviewCheckoutInfo name="firstName" label="First Name" order="${order}"/>
        <tags:overviewCheckoutInfo name="lastName" label="Last Name" order="${order}"/>
        <tags:overviewCheckoutInfo name="phone" label="Phone number" order="${order}"/>
        <tags:overviewCheckoutInfo name="deliveryAddress" label="Delivery Address" order="${order}"/>
        <tr>
            <td>Date: </td>
            <td>
                    ${order.deliveryOrderDetail.deliveryDate}
            </td>
        </tr>
        <tr>
            <td>Payment method:</td>
            <td>
                    ${order.paymentType}
            </td>
        </tr>
    </table>


</tags:master>