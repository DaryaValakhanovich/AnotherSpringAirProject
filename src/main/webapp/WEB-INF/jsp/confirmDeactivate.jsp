<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.10.2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>

<h3>Are you absolutely sure that you want to deactivate ticket?</h3>
<form:form method="POST">
    <table border="0">
        <tr>
            <td><a href="${pageContext.request.contextPath}/showMyTickets/${pageContext.request.userPrincipal.name}">Back</a></td>
            <td></td>
            <td><input type="submit" value="Deactivate!"></td>
        </tr>
    </table>
    <input  style="display: none" name="ticketId" value= ${ticketId}>

</form:form>
</body>
</html>
