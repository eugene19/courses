<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="courses.list.empty" var="list_empty"/>
<fmt:message bundle="${bundle}" key="courses.summary" var="courses_summary"/>
<fmt:message bundle="${bundle}" key="courses.button.add" var="add_course"/>

<div class="container">
    <div class="row align-items-center justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${courses_summary}</h3>
    </div>

    <c:if test="${error != null}">
        <div class="row align-items-center justify-content-center py-5">
            <div class="alert alert-danger w-100 text-center py-2" role="alert">
                    ${error}
            </div>
        </div>
    </c:if>

    <c:if test="${empty courseList}">
        <div class="row align-items-center justify-content-center py-5">
            <div class="alert alert-info w-100 text-center py-2" role="alert">
                    ${list_empty}
            </div>
        </div>
    </c:if>

    <c:if test="${user.role == 'LECTURER'}">
        <div class="row ml-1 mb-3">
            <a href="${pageContext.request.contextPath}/main?command=get_add_course_page">
                <button class="btn btn-outline-primary">${add_course}</button>
            </a>
        </div>
    </c:if>

    <div class="row">
        <c:forEach var="course" items="${courseList}">
            <div class="col col-md-4">
                <div class="col border border-light shadow-sm m-1"
                     style="height: 160px">
                    <div class="row font-weight-bold p-2">
                            ${course.summary}
                    </div>
                    <div class="row p-2">
                        <c:choose>
                            <c:when test="${course.description.length() > 100}">
                                ${course.description.substring(0, 100)}...
                            </c:when>
                            <c:otherwise>
                                ${course.description}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row text-muted p-2">
                            ${course.startDate} - ${course.endDate}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>