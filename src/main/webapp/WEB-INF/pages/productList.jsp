<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>

  <form action="" method="get">
    <label>
      <input type="text" name="query" value="${param.query}" placeholder="Search here...">
    </label>
  </form>
  <table>
    <thead>
    <tr>
      <td>Image</td>
      <td>
        Description
        <tags:sortLink order="asc" sort="description"/>
        <tags:sortLink order="desc" sort="description"/>
      </td>
      <td>Quantity</td>
      <td class="price">
        Price
        <tags:sortLink order="asc" sort="price"/>
        <tags:sortLink order="desc" sort="price"/>
      </td>
      <td></td>
    </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <form name="add" action="${pageContext.request.contextPath}/products" method="post">
        <tr>
          <td>
            <img class="product-tile" src="${product.imageUrl}">
          </td>

          <td>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                ${product.description}
            </a>
          </td>

          <td>
            <input name="quantity" value="1">
            <input type="hidden" value="${product.id}" name="productId">
            <c:if test="${not empty errors[product.id]}">
              <div class="error">
                  ${errors[product.id]}
              </div>
            </c:if>
          </td>

          <td class="price">
            <a href="${pageContext.servletContext.contextPath}/productPriceHistory/${product.id}">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </a>
          </td>

          <td><input type="submit" name="addButton" value="Add to cart"></td>

        </tr>
      </form>

    </c:forEach>
  </table>


  <h1>Recently viewed</h1>
  <table>
    <thead>
    <c:forEach var="product" items="${viewProducts}">
      <td>
        <img class="product-tile"
             src="${product.imageUrl}">
        <br>
        <a href="<c:url value="/products/${product.id}"/>">
            ${product.description}
        </a>
        <br>
        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
      </td>
    </c:forEach>
    </thead>
  </table>
</tags:master>