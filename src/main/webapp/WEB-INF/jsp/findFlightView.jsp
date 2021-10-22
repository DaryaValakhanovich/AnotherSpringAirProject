<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Find Flight</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>


<h3>Find Flight</h3>

<p style="color: red;">${errorString}</p>

<form:form method="POST" modelAttribute="flight">
    <table border="0">
        <tr>
            <td>Departure date</td>
            <td><form:input type="date" path="departure" placeholder="departure"></form:input></td>
        </tr>
        <tr>
            <td>Number of seats</td>
            <td><form:input type="number" path="numberOfFreeSeats" placeholder="numberOfFreeSeats"></form:input></td>
        </tr>
        <tr>
            <td>Start airport</td>
            <td><form:input type="text" path="startAirport" placeholder="startAirport"></form:input></td>
        </tr>
        <tr>
            <td>Final airport</td>
            <td><form:input type="text" path="finalAirport" placeholder="finalAirport"></form:input></td>
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