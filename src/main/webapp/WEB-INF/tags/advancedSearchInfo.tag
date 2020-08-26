<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>

<tr>
    <td>${label}:</td>
    <td>
        <input name="${name}" value="${param[name]}">
        <c:if test="${not empty errors.get(name)}">
            <c:forEach var="error" items="${errors.get(name)}">
                <span style="color: red">${error}</span>
            </c:forEach>
        </c:if>
    </td>
</tr>