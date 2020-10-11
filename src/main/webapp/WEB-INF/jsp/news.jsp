<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>

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
<fmt:message bundle="${bundle}" key="news.1.title" var="news_1_title"/>
<fmt:message bundle="${bundle}" key="news.1.description"
             var="news_1_description"/>
<fmt:message bundle="${bundle}" key="news.2.title" var="news_2_title"/>
<fmt:message bundle="${bundle}" key="news.2.description"
             var="news_2_description"/>
<fmt:message bundle="${bundle}" key="news.3.title" var="news_3_title"/>
<fmt:message bundle="${bundle}" key="news.3.description"
             var="news_3_description"/>
<fmt:message bundle="${bundle}" key="news.4.title" var="news_4_title"/>
<fmt:message bundle="${bundle}" key="news.4.description"
             var="news_4_description"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${news_summary}</h3>
    </div>

    <div class="row">
        <div class="col col-md-4 my-3">
            <div class="card" style="width: 21rem; max-height: 400px;">
                <img src="${pageContext.request.contextPath}/picture/news/1.jpg"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title font-weight-bold">
                        <myTag:cutLongText text="${news_1_title}"
                                           maxLength="40"/>
                    </h5>
                    <p class="card-text">
                        <myTag:cutLongText text="${news_1_description}"
                                           maxLength="150"/></p>
                    <a href="#" class="stretched-link"></a>
                </div>
            </div>
        </div>

        <div class="col col-md-4 my-3">
            <div class="card" style="width: 21rem; max-height: 400px;">
                <img src="${pageContext.request.contextPath}/picture/news/2.png"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title font-weight-bold">
                        <myTag:cutLongText text="${news_2_title}"
                                           maxLength="40"/>
                    </h5>
                    <p class="card-text">
                        <myTag:cutLongText text="${news_2_description}"
                                           maxLength="125"/></p>
                    <a href="#" class="stretched-link"></a>
                </div>
            </div>
        </div>

        <div class="col col-md-4 my-3">
            <div class="card" style="width: 21rem; max-height: 400px;">
                <img src="${pageContext.request.contextPath}/picture/news/3.png"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title font-weight-bold">
                        <myTag:cutLongText text="${news_3_title}"
                                           maxLength="40"/>
                    </h5>
                    <p class="card-text">
                        <myTag:cutLongText text="${news_3_description}"
                                           maxLength="150"/></p>
                    <a href="#" class="stretched-link"></a>
                </div>
            </div>
        </div>

        <div class="col col-md-4 my-3">
            <div class="card" style="width: 21rem; max-height: 400px;">
                <img src="${pageContext.request.contextPath}/picture/news/4.jpg"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title font-weight-bold">
                        <myTag:cutLongText text="${news_4_title}"
                                           maxLength="40"/>
                    </h5>
                    <p class="card-text">
                        <myTag:cutLongText text="${news_4_description}"
                                           maxLength="150"/></p>
                    <a href="#" class="stretched-link"></a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
