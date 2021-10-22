<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.10.2021
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>My Tickets</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>


<form:form method="GET" modelAttribute="allTickets">
    <style>
        th {
            font-weight: normal;
            color: #039;
            padding: 10px 15px;
        }
        td {
            color: #669;
            border-top: 1px solid #e8edff;
            padding: 10px 15px;
        }
        tr:hover td {
            background: #e8edff;
        }
    </style>
    <table>
        <tr>
            <th>Flight Id</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Start Airport</th>
            <th>Final Airport</th>
            <th>Price</th>
            <th>Plane</th>
            <th>Show plane</th>
            <th>Number Of Seats</th>
            <th>Show Seats</th>
            <td>Active</td>
            <th>Deactivate ticket</th>
        </tr>
        <c:forEach items="${allTickets}" var="ticket">
            <tr>
                <td>${ticket.flight.id}</td>
                <td>${ticket.flight.departure}</td>
                <td>${ticket.flight.arrival}</td>
                <td>${ticket.flight.startAirport}</td>
                <td>${ticket.flight.finalAirport}</td>
                <td>${ticket.flight.price}</td>
                <td>${ticket.flight.plane.model}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/planes/showPlane/${ticket.flight.plane.id}">show</a>
                </td>
                <td>${ticket.numberOfSeats}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/showSeats/${ticket.id}">show</a>
                </td>
                <td>
                    <c:if test="${ticket.active==true}">
                        &#8730
                    </c:if>
                    <c:if test="${ticket.active==false}">
                        &#215
                    </c:if></td>
                <td>
                    <a href="${pageContext.request.contextPath}/deactivate/${ticket.id}">deactivate</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</form:form>


    <a href="${pageContext.request.contextPath}/">Cancel</a>
        <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
