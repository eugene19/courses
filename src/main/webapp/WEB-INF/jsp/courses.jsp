<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

    <c:if test="${user.role == 'LECTURER'}">
        <div class="row ml-1 mb-3">
            <a href="${pageContext.request.contextPath}/main?command=get_add_course_page">
                <button class="btn btn-outline-primary">${add_course}</button>
            </a>
        </div>
    </c:if>

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

    <div class="row">
        <c:forEach var="course" items="${courseList}">
            <div class="col col-md-4">
                <div class="col border border-light shadow-sm m-1"
                     style="height: 160px">
                    <div class="row font-weight-bold p-2">
                        <div class="col col-md-10 pl-0 align-middle">
                            <c:choose>
                                <c:when test="${course.summary.length() > 25}">
                                    ${course.summary.substring(0, 25)}...
                                </c:when>
                                <c:otherwise>
                                    ${course.summary}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:if test="${course.lecturerId == user.id}">
                            <div class="col col-md-1 p-0 m-0">
                                <form class="m-0"
                                      action="${pageContext.request.contextPath}/main"
                                      method="post">
                                    <input type="hidden" name="command"
                                           value="get_edit_course_page">
                                    <input type="hidden" name="courseId"
                                           value="${course.id}">

                                    <button class="btn mb-0" type="submit"><i
                                            class="fa fa-edit text-info"></i>
                                    </button>
                                </form>
                            </div>
                            <div class="col col-md-1 p-0 m-0">
                                <form class="m-0"
                                      action="${pageContext.request.contextPath}/main"
                                      method="post">
                                    <input type="hidden" name="command"
                                           value="delete_course">
                                    <input type="hidden" name="courseId"
                                           value="${course.id}">

                                    <button class="btn mb-0" type="submit"><i
                                            class="fa fa-trash text-danger"></i>
                                    </button>
                                </form>
                            </div>
                        </c:if>
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
                    <div class="row text-muted p-2 small">
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