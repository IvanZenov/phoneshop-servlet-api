<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
  <p>
    Product Page
  </p>
  <p>
    Cart: ${cart}
  </p>
  <p>
    ${product.description}
  </p>

  <c:if test="${not empty operationResult}">
    <p class="success">
        ${operationResult}
    </p>
  </c:if>

  <c:if test="${not empty error}">
    <p class="error">
      Problems adding a product :(
    </p>
  </c:if>


    <form method="post" action="${pageContext.request.contextPath}/products/${product.id}">
      <table>
        <tr>
          <td>
            <td>Image</td>
          <td><img src="${product.imageUrl}"></td>
          </td>
        </tr>
          <tr>
            <td>
              <td>Code</td>
              <td>${product.code}</td>
            </td>
          </tr>
          <tr>
            <td class="price">
              <td>Price</td>
              <td>${product.price}</td>
            </td>
          </tr>
          <tr>
            <td>
              <td>Stock</td>
              <td>${product.stock}</td>
            </td>
          </tr>
        <tr>
          <td>
            <td>Quantity</td>
            <td>
              <input name="quantity" value="${not empty error ? param.quantity : 1}">
              <c:if test="${not empty error}">
                <p class="error">
                  ${error}
                </p>
              </c:if>
            </td>
          </td>
        </tr>
    </table>
      <button type="submit">Add to cart</button>
  </form>


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