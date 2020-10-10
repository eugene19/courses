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

<fmt:message bundle="${bundle}" key="errorPage.message" var="message"/>
<fmt:message bundle="${bundle}" key="button.goToHomePage"
             var="go_to_home_button"/>

<div class="container">
    <div class="row justify-content-center py-5 mt-5">
        <div class="alert alert-danger col-lg-12 mt-5 text-center" role="alert">
            ${message}
        </div>
    </div>
    <div class="row justify-content-center pb-5">
        <a href="../../index.jsp">
            <button class="btn btn-outline-info">${go_to_home_button}</button>
        </a>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
