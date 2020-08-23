<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
    <p style="font-weight: bold">
        Price history
    </p>
    <p>${product.description}</p>
    <table>

        <c:forEach var="info" items="${product.histories}">
        <tr>
            <td>
                Start date
                <p>${info.startDate}</p>
            </td>
            <td class="price">
                Price
                <p>${info.price}</p>
            </td>
        </tr>
        </c:forEach>
</tags:master>