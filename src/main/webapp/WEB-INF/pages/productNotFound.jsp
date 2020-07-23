<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Product List">
    <a href="${pageContext.servletContext.contextPath}/products">Back to product list</a>
    <h4>
        Sorry, but product not found
    </h4>
</tags:master>