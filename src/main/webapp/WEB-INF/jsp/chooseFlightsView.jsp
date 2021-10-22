
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suitable flights</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>

<script>
function Validate(){
if(!validateForm()){
alert("You must check one of the flights");
return false;
}
return true
}
function validateForm()
{
var c=document.getElementsByTagName('input');
for (var i = 0; i<c.length; i++){
if (c[i].type=='radio')
{
if (c[i].checked){return true}
}
}
return false;
}
</script>

<h3>Suitable flights</h3>

<p style="color: red;">${errorString}</p>
<form method="POST" action="${pageContext.request.contextPath}/createTicket" onsubmit="return Validate()">
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
        <th></th>
        <th>Id</th>
        <th>Departure</th>
        <th>Arrival</th>
        <th>Start Airport</th>
        <th>Final Airport</th>
        <th>Plane</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${flights}" var="flight">
        <tr>
            <td><input type="radio" name="flightId" value=${flight.id}></td>
            <td>${flight.id}</td>
            <td>${flight.departure}</td>
            <td>${flight.arrival}</td>
            <td>${flight.startAirport}</td>
            <td>${flight.finalAirport}</td>
            <td>${flight.plane.model}</td>
            <td>${flight.price}</td>
        </tr>
    </c:forEach>
</table>
    <input  style="display: none" type="number" name="numberOfSeats" value= ${numberOfSeats}>
    <input  style="display: none" type="text" name="email" value= ${pageContext.request.userPrincipal.name}>
    <a href="${pageContext.request.contextPath}/">Cancel           </a>
    <p><input type="submit" value="Отправить">
<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>