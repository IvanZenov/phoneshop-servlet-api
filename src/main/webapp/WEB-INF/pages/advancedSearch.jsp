<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <form method="post" action="${pageContext.request.contextPath}/advancedSearch">
        <table>
            <tags:advancedSearchInfo name="code" label="Product Code" errors="${errors}"/>
            <tags:advancedSearchInfo name="minPrice" label="Min Price" errors="${errors}"/>
            <tags:advancedSearchInfo name="maxPrice" label="Max price" errors="${errors}"/>
            <tags:advancedSearchInfo name="minStock" label="Min Stock" errors="${errors}"/>
            <tr>
                <td>
                    <button type="submit">Search</button>
                </td>
            </tr>
        </table>


    </form>

    <c:if test="${not empty products}">
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
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="products/${product.id}">${product.description}</a>
                    </td>
                    <td class="price">
                        <a href="products/priceHistory/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master>