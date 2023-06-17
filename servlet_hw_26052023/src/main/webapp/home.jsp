<%@ page import="by.teachmeskills.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Добро пожаловать на домашнюю страницу!</title>
</head>
<body>
<% User user = (User) session.getAttribute("user");%>
<c:if test="${user == null}">
    <jsp:forward page="/login"/>
</c:if>
</body>
</html>