<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Plane</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>


<h3>Create Plane</h3>

<p style="color: red;">${errorString}</p>
<form:form method="POST" modelAttribute="newPlane">
    <table border="0">
        <tr>
            <td>Number of seats</td>
            <td><form:input type="number" path="numberOfSeats" placeholder="numberOfSeats"></form:input></td>
        </tr>
        <tr>
            <td>Weight</td>
            <td><form:input type="number" path="weight" placeholder="weight"></form:input></td>
        </tr>
        <tr>
            <td>Cruising Speed</td>
            <td><form:input type="number" path="cruisingSpeed" placeholder="cruisingSpeed"></form:input></td>
        </tr>
        <tr>
            <td>Model</td>
            <td><form:input type="text" path="model" placeholder="model"></form:input></td>
        </tr>
        <tr>
            <td>Company</td>
            <td><form:input type="text" path="company" placeholder="company"></form:input></td>
        </tr>
        <tr>
            <td>Max Flight Altitude</td>
            <td><form:input type="number" path="maxFlightAltitude" placeholder="maxFlightAltitude"></form:input></td>
        </tr>
        <tr>
            <td>Max Range Of Flight</td>
            <td><form:input type="number" path="maxRangeOfFlight" placeholder="maxRangeOfFlight"></form:input></td>
        </tr>

        <tr>
            <td colspan="2">
                <a href="${pageContext.request.contextPath}/">Cancel</a>
                <input type="submit" value="Submit" />
            </td>
        </tr>
    </table>
</form:form>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
