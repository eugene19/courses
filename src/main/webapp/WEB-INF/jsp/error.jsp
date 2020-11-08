<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<html>
<head>
    <title>Error</title>
    <%@include file="component/link_style.jsp" %>
</head>
<body>

<c:if test="${empty user}">
    <c:set var="user" value="${null}"/>
</c:if>

<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="errorPage.message" var="message"/>
<fmt:message bundle="${bundle}" key="button.goToHomePage" var="go_home_btn"/>

<div class="container">
    <div class="row justify-content-center py-5 mt-5">
        <div class="alert alert-danger col-lg-12 mt-5 text-center" role="alert">
            ${message}
        </div>
    </div>
    <div class="row justify-content-center pb-5">
        <a href="../../index.jsp">
            <button class="btn btn-outline-info">${go_home_btn}</button>
        </a>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
