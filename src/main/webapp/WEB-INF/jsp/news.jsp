<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>News</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="news.summary" var="news_summary"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${news_summary}</h3>
    </div>

    <div class="row justify-content-center py-5">
        ${news}
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
