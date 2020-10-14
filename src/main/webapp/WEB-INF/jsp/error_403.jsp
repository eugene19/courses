<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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

<fmt:message bundle="${bundle}" key="errorPage.message.403" var="message"/>
<fmt:message bundle="${bundle}" key="button.goToHomePage" var="go_home_btn"/>
<fmt:message bundle="${bundle}" key="login.submit" var="login_button"/>
<fmt:message bundle="${bundle}" key="header.registration"
             var="register_btn"/>

<div class="container">
    <div class="row justify-content-center py-5 mt-5">
        <div class="alert alert-danger col-lg-12 mt-5 text-center" role="alert">
            ${message}
        </div>
    </div>
    <div class="row justify-content-center pb-5">
        <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/main?command=get_login_page">
                <button class="btn btn-outline-info mx-1">${login_button}</button>
            </a>
            <a href="${pageContext.request.contextPath}/main?command=get_registration_page">
                <button class="btn btn-outline-info mx-1">${register_btn}</button>
            </a>
        </c:if>
        <a href="../../index.jsp">
            <button class="btn btn-outline-info mx-1">${go_home_btn}</button>
        </a>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
