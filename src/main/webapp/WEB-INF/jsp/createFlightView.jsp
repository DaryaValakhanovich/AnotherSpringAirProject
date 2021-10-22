<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Flight</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>

<script>
    function Validate(){
        if(!validateForm()){
            alert("You must check one of the planes");
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

<h3>Create Flight</h3>

<p style="color: red;">${errorString}</p>

<form:form method="POST" modelAttribute="newFlight" onsubmit="return Validate()">
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
    <table border="0">
        <tr>
            <td>Departure date</td>
            <td><form:input type="datetime-local" path="departure" placeholder="departure"></form:input>
            </td>
        </tr>
        <tr>
            <td>Arrival date</td>
            <td><form:input type="datetime-local" path="arrival" placeholder="arrival"></form:input></td>
        </tr>
        <tr>
            <td>Start airport</td>
            <td><form:input type="text" path="startAirport" placeholder="startAirport"></form:input></td>
        </tr>
        <tr>
            <td>Final airport</td>
            <td><form:input type="text" path="finalAirport" placeholder="finalAirport"></form:input></td>
        </tr>
    </table>
    <table border="0">
        <tr>
            <td colspan="3">Choose plane:</td>
        </tr>
        <tr>
            <td></td>
            <td>Plane id</td>
            <td>Plane model</td>
        </tr>
        <c:forEach items="${allPlanes}" var="plane">
            <tr>
                <td>
                    <input type="radio" name="planeId" value=${plane.id}></td>
                <td>${plane.id}</td>
                <td>${plane.model}</td>
            </tr>
        </c:forEach>
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