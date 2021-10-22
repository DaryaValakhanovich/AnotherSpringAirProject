

<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.10.2021
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Suitable flights</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>

<script>
    function validate(){
        if(!validateForm()){
            alert("You must check at least one of the flights");
            return false;
        }
        return true
    }
    function validateForm()
    {
        var c=document.getElementsByTagName('input');
        for (var i = 0; i<c.length; i++){
            if (c[i].type=='checkbox')
            {
                if (c[i].checked){return true}
            }
        }
        return false;
    }
</script>

<h3>There are no suitable flights for you!!!</h3>
<h3>How about a transfer option?</h3>
<p style="color: red;">${errorString}</p>
<form method="POST" action="${pageContext.request.contextPath}/createTransferTicket">
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
        <c:forEach items="${listsOfFlights}" var="listOfFlights">
            <td colspan="8"><input type="radio" name="flightListIndex" value=${listsOfFlights.indexOf(listOfFlights)}></td>
              <td>        <c:forEach items="${listOfFlights}" var="flight">
                          <tr>
                              <td></td>
                          <td>${flight.id}</td>
                          <td>${flight.departure}</td>
                          <td>${flight.arrival}</td>
                          <td>${flight.startAirport}</td>
                          <td>${flight.finalAirport}</td>
                          <td>${flight.plane.model}</td>
                          <td>${flight.price}</td>
                          </tr>
                      </c:forEach>
                 </td>
            <tr>
                <td colspan="8"></td>
            </tr>
              </c:forEach>
          </table>

    <a href="${pageContext.request.contextPath}/">Cancel      </a>
    <p><input type="submit" value="Отправить"></p>
          <input style="display: none" type="number" name="numberOfSeats" value= ${numberOfSeats}>
    <input  style="display: none" type="text" name="email" value= ${pageContext.request.userPrincipal.name}>
    <input  style="display: none" name="flightsDto" value= ${flightsDto}>
    <input  style="display: none" type="date" name="departure" value= ${flightsDto.departure}>
    <input  style="display: none" type="text" name="startAirport" value= ${flightsDto.startAirport}>
    <input  style="display: none" type="text" name="finalAirport" value= ${flightsDto.finalAirport}>

</form>
              <jsp:include page="_footer.jsp"></jsp:include>
      </body>
      </html>