
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.09.2021
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
    <div style="float: left">
        <h1>My Site</h1>
    </div>

    <div style="float: right; padding: 10px; text-align: right;">

        <!-- User store in session with attribute: loginedUser -->
        Hello <b> ${loginedUser.email}</b>

    </div>

</div>
<sec:authorize access="!isAuthenticated()">
    <jsp:include page="_menu.jsp"></jsp:include>
</sec:authorize>
<c:if test="${pageContext.request.isUserInRole('USER')}">
    <jsp:include page="_user_menu.jsp"></jsp:include>
</c:if>
<c:if test="${pageContext.request.isUserInRole('ADMIN')}">
    <jsp:include page="_admin_menu.jsp"></jsp:include>
</c:if>