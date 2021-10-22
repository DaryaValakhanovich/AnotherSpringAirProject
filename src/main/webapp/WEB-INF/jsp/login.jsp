<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
</head>

<body>
<jsp:include page="_header.jsp"></jsp:include>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>
<p style="color: red;">${errorString}</p>
<div>
  <form method="POST" action="/login">
    <h2>Вход в систему</h2>
    <table border="0">
      <tr>
        <td>Login</td>
        <td><input name="username" type="text" placeholder="Email"
                   autofocus="true"/> </td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input name="password" type="password" placeholder="Password"/> </td>
      </tr>
      <tr>
        <td>
          <a href="${pageContext.request.contextPath}/">Cancel</a>
        </td>
         <td> <button type="submit">Log In</button></td>
      </tr>
    </table>
  </form>
</div>

</body>
</html>
