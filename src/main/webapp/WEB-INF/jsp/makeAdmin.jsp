<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Make admin</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>


<h3>Make admin</h3>

<p style="color: red;">${errorString}</p>

<form:form method="POST" modelAttribute="userForEmail">
    <table border="0">
        <tr>
            <td colspan="2">Email</td>
            <td><div><form:input type="text" path="email" placeholder="Email"></form:input></div></td>
        </tr>
    <tr>
        <td colspan="2">
            <a href="${pageContext.request.contextPath}/">Cancel</a>
        </td>
            <td>
                <input type="submit" value="Submit" />
            </td>
        </tr>
    </table>
</form:form>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>