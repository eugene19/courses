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

    <%--        1 news--%>

    <div class="row">
        <div class="col col-md-4">
            <div class="col border border-light shadow-sm m-1"
                 style="height: 400px">
                <div class="row p-2">
                    <img style="height: 185px;"
                         src="${pageContext.request.contextPath}/picture/news/1.jpg"
                         alt="News img"/>
                </div>
                <div class="row font-weight-bold p-2">
                    <c:choose>
                        <c:when test="${news_1_title.length() > 40}">
                            ${news_1_title.substring(0, 40)}...
                        </c:when>
                        <c:otherwise>
                            ${news_1_title}
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row p-2">
                    <c:choose>
                        <c:when test="${news_1_description.length() > 150}">
                            ${news_1_description.substring(0, 150)}...
                        </c:when>
                        <c:otherwise>
                            ${news_1_description}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <%--        2 news--%>

        <div class="col col-md-4">
            <div class="col border border-light shadow-sm m-1"
                 style="height: 400px">
                <div class="row p-2">
                    <img style="height: 185px;"
                         src="${pageContext.request.contextPath}/picture/news/2.png"
                         alt="News img"/>
                </div>
                <div class="row font-weight-bold p-2">
                    <c:choose>
                        <c:when test="${news_2_title.length() > 40}">
                            ${news_2_title.substring(0, 40)}...
                        </c:when>
                        <c:otherwise>
                            ${news_2_title}
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row p-2">
                    <c:choose>
                        <c:when test="${news_2_description.length() > 150}">
                            ${news_2_description.substring(0, 150)}...
                        </c:when>
                        <c:otherwise>
                            ${news_2_description}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <%--        3 news--%>

        <div class="col col-md-4">
            <div class="col border border-light shadow-sm m-1"
                 style="height: 400px">
                <div class="row p-2">
                    <img style="height: 185px;"
                         src="${pageContext.request.contextPath}/picture/news/3.png"
                         alt="News img"/>
                </div>
                <div class="row font-weight-bold p-2">
                    <c:choose>
                        <c:when test="${news_3_title.length() > 40}">
                            ${news_3_title.substring(0, 40)}...
                        </c:when>
                        <c:otherwise>
                            ${news_3_title}
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row p-2">
                    <c:choose>
                        <c:when test="${news_3_description.length() > 150}">
                            ${news_3_description.substring(0, 150)}...
                        </c:when>
                        <c:otherwise>
                            ${news_3_description}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <%--        4 news--%>

        <div class="col col-md-4">
            <div class="col border border-light shadow-sm m-1"
                 style="height: 400px">
                <div class="row p-2">
                    <img style="height: 185px;"
                         src="${pageContext.request.contextPath}/picture/news/4.jpg"
                         alt="News img"/>
                </div>
                <div class="row font-weight-bold p-2">
                    <c:choose>
                        <c:when test="${news_4_title.length() > 40}">
                            ${news_4_title.substring(0, 40)}...
                        </c:when>
                        <c:otherwise>
                            ${news_4_title}
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row p-2">
                    <c:choose>
                        <c:when test="${news_4_description.length() > 150}">
                            ${news_4_description.substring(0, 150)}...
                        </c:when>
                        <c:otherwise>
                            ${news_4_description}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
