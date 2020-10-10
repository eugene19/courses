<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>About us</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="about.summary" var="about_summary"/>
<fmt:message bundle="${bundle}" key="about.overview" var="about_overview"/>
<fmt:message bundle="${bundle}" key="about.leader.title"
             var="about_leader_title"/>
<fmt:message bundle="${bundle}" key="about.leader" var="about_leader"/>
<fmt:message bundle="${bundle}" key="about.opportunity.title"
             var="about_opportunity_title"/>
<fmt:message bundle="${bundle}" key="about.opportunity"
             var="about_opportunity"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${about_summary}</h3>
    </div>

    <div class="row py-5">
        ${about_overview}
    </div>

    <div class="row py-5">
        <h3 class="h3 mb-3 font-weight-normal">${about_leader_title}</h3>
        ${about_leader}
    </div>

    <div class="row py-5">
        <h3 class="h3 mb-3 font-weight-normal">${about_opportunity_title}</h3>
        ${about_opportunity}
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
