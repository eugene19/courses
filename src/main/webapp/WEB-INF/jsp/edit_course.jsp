<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit course</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="courses.edit.title" var="page_title"/>
<fmt:message bundle="${bundle}" key="courses.add.summary" var="summary_lable"/>
<fmt:message bundle="${bundle}" key="courses.add.description"
             var="description_lable"/>
<fmt:message bundle="${bundle}" key="courses.add.startDate"
             var="start_date_lable"/>
<fmt:message bundle="${bundle}" key="courses.add.endDate" var="end_date_lable"/>
<fmt:message bundle="${bundle}" key="courses.add.studentsLimit"
             var="students_limit_lable"/>
<fmt:message bundle="${bundle}" key="profile.edit.button"
             var="try_create_button"/>

<div class="container">
    <div class="row align-items-center justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${page_title}</h3>
    </div>

    <div class="row align-items-center justify-content-center py-2">
        <form class="col-md-3" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="edit_course"/>
            <input type="hidden" name="id"
                   value="<c:if test="${init.id[0] != null}">${init.id[0]}</c:if><c:if test="${init.id[0] == null}">${course.id}</c:if>">

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </c:if>

            <div class="form-group row">
                <label for="summary">${summary_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="summary" name="summary"
                       value="<c:if test="${init.summary[0] != null}">${init.summary[0]}</c:if><c:if test="${init.summary[0] == null}">${course.summary}</c:if>"/>
                <span class="text-danger small">${errors.summary} </span>
            </div>

            <div class="form-group row">
                <label for="description">${description_lable}</label>
                <textarea class="form-control form-control-sm" id="description"
                          rows="3"
                          name="description"><c:if
                        test="${init.description[0] != null}">${init.description[0]}</c:if><c:if
                        test="${init.description[0] == null}">${course.description}</c:if></textarea>
                <div class="text-danger small">${errors.description} </div>
            </div>

            <div class="form-group row">
                <label for="startDate">${start_date_lable}</label>
                <input type="date" class="form-control form-control-sm"
                       id="startDate"
                       name="startDate"
                       value="<c:if test="${init.startDate[0] != null}">${init.startDate[0]}</c:if><c:if test="${init.startDate[0] == null}">${course.startDate}</c:if>"/>
                <span class="text-danger small">${errors.startDate} </span>
            </div>

            <div class="form-group row">
                <label for="endDate">${end_date_lable}</label>
                <input type="date" class="form-control form-control-sm"
                       id="endDate" name="endDate"
                       value="<c:if test="${init.endDate[0] != null}">${init.endDate[0]}</c:if><c:if test="${init.endDate[0] == null}">${course.endDate}</c:if>"/>
                <span class="text-danger small">${errors.endDate} </span>
            </div>

            <div class="form-group row">
                <label for="studentsLimit">${students_limit_lable}</label>
                <input class="form-control" id="studentsLimit" type="number"
                       name="studentsLimit"
                       value="<c:if test="${init.studentsLimit[0] != null}">${init.studentsLimit[0]}</c:if><c:if test="${init.studentsLimit[0] == null}">${course.studentsLimit}</c:if>"/>
                <div class="text-danger small">${errors.studentsLimit} </div>
            </div>

            <div class="row align-items-center justify-content-center py-4">
                <button class="btn btn-outline-info"
                        type="submit">${try_create_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
