<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.10.2021
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>


<h3>Plane information</h3>

<table border="1" cellpadding="5" cellspacing="1" >
        <tr>
            <th>Number Of Seats</th>
            <th>Weight</th>
            <th>Cruising Speed</th>
            <th>Model</th>
            <th>Company</th>
            <th>Max Flight Altitude</th>
            <th>Max Range Of Flight</th>
        </tr>
        <tr>
            <td>${plane.numberOfSeats}</td>
            <td>${plane.weight}</td>
            <td>${plane.cruisingSpeed}</td>
            <td>${plane.model}</td>
            <td>${plane.company}</td>
            <td>${plane.maxFlightAltitude}</td>
            <td>${plane.maxRangeOfFlight}</td>
        </tr>

</table>
<a href="${pageContext.request.contextPath}/showMyTickets">Back</a>
<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
