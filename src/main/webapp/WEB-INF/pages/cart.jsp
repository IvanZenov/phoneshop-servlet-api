<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <c:if test="${not empty success}">
        <p class="success">
                ${success}
        </p>
    </c:if>

    <c:if test="${not empty error}">
        <p class="error">
            Problems adding a product :(
        </p>
    </c:if>

    <form action="${pageContext.request.contextPath}/cart" method="post">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description

                </td>
                <td class="price">
                    Price
                </td>

                <td class="price">
                    Quantity
                </td>
            </tr>
            </thead>

            <c:forEach var="item" items="${cart.items}" varStatus="status">
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
                        <a href="${pageContext.servletContext.contextPath}/productPriceHistory/${item.product.id}">
                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                        </a>
                    </td>

                    <td class="price">
                        <fmt:formatNumber value="${item.quantity}" var = "quantity"/>
                        <c:set var="error" value="${errors[item.product.id]}"/>
                        <input name="quantity" value="${not empty error ? paramValues['quantity'][status.index]: item.quantity}"/>

                        <c:if test="${not empty errors[item.product.id]}">
                            <div class="error">
                                    ${error}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${item.product.id  }">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <button>Update</button>
        </p>
    </form>

</tags:master>