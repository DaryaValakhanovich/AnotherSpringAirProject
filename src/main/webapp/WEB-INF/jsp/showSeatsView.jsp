<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.10.2021
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seats</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>

<h3>Seats</h3>

<p style="color: red;">${errorString}</p>
<form:form method="GET" modelAttribute="seats">
<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>Number Of Seat</th>
        <th>Ticket Id</th>
    </tr>
    <c:forEach items="${seats}" var="s" >
        <tr>
            <td>${s.numberOfSeat}</td>
            <td>${s.ticket.id}</td>
        </tr>
    </c:forEach>
</table>
</form:form>
<a href="${pageContext.request.contextPath}/showMyTickets/${pageContext.request.userPrincipal.name}">Back</a>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>