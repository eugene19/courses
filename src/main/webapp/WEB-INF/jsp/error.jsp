<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        <%@include file="../css/style.css"%>
    </style>
</head>
<body>
<%-- Костыль --%>
<c:if test="${empty user}">
    <c:set var="user" value="${null}"></c:set>
</c:if>

<%@include file="component/header.jsp" %>
<%@include file="component/menu.jsp" %>

<fmt:message bundle="${bundle}" key="errorPage.message" var="message"/>
<fmt:message bundle="${bundle}" key="errorPage.button" var="button"/>

<div class="center-form">
    <p>${message}</p><br/>
    <a href="../../index.jsp">
        <button>${button}</button>
    </a>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
