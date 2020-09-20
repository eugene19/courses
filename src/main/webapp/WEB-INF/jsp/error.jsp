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

<div class="center-form">
    Sorry, something goes wrong...<br/>
    <a href="../../index.jsp"><button>Go to the start page</button></a>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
