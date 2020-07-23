<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Product Page
  </p>
  <table>
    <tr>
      <td>
        Image
        <img src="${product.imageUrl}">
      </td>
      <td>
        Code
        <p>${product.code}</p>
      </td>
      <td class="price">
        Price
        <p>${product.price}</p>
      </td>

      <td>
        Stock
        <p>${product.stock}</p>
      </td>
    </tr>
  </table>


</tags:master>