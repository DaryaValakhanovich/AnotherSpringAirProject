<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.10.2021
  Time: 17:36
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

<h3>Are you absolutely sure that you want to log out?</h3>
<form method="POST" action="${pageContext.request.contextPath}/logout">
    <table border="0">
        <tr>
            <td><a href="${pageContext.request.contextPath}/home">Cancel</a></td>
            <td></td>
            <td><input type="submit" value="Log out!"></td>
        </tr>
    </table>
</form>
</body>
</html>
