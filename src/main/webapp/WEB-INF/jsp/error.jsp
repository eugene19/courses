<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%-- Костыль --%>
<c:if test="${empty user}">
    <c:set var="user" value="${null}"/>
</c:if>

<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="errorPage.message" var="message"/>
<fmt:message bundle="${bundle}" key="errorPage.button" var="button"/>

<div class="center-form">
    <div><p>${message}</p></div>
    <div>
        <a href="../../index.jsp">
            <button>${button}</button>
        </a>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
