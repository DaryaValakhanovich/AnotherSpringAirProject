<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.09.2021
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div style="padding: 5px;">

  <a href="${pageContext.request.contextPath}/">Home</a>
  |
  <a href="${pageContext.request.contextPath}/flights/findFlight">Find flight</a>

  |
  <a href="${pageContext.request.contextPath}/showMyTickets/${pageContext.request.userPrincipal.name}">Show my tickets</a>
  |
  <a href="${pageContext.request.contextPath}/logout">Log out</a>

</div>
