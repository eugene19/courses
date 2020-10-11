<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="course" scope="request"
             class="by.epamtc.courses.entity.Course"/>

<html>
<head>
    <title>Course details</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="course.details.summary"
             var="details_summary"/>
<fmt:message bundle="${bundle}" key="button.edit"
             var="edit_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${details_summary}</h3>
    </div>

    <c:if test="${course.lecturerId == user.id}">
        <div class="row ml-2">
            <a class="btn btn-outline-primary"
               href="${pageContext.request.contextPath}/main?command=get_edit_course_page&courseId=${course.id}">
                <i class="fa fa-edit text-primary"></i> ${edit_button}
            </a>
        </div>
    </c:if>

    <div class="row py-5">
        <div class="container-fluid">
            <h5 class="h5 mb-5 font-weight-normal">${course.summary}</h5>
        </div>
        <div class="container-fluid">
            ${course.description}
        </div>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
