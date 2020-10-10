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
<fmt:message bundle="${bundle}" key="courses.status"
             var="status_lable"/>
<fmt:message bundle="${bundle}" key="course.status.notStarted"
             var="status_not_started"/>
<fmt:message bundle="${bundle}" key="course.status.inProgress"
             var="status_in_progress"/>
<fmt:message bundle="${bundle}" key="course.status.finished"
             var="status_finished"/>
<fmt:message bundle="${bundle}" key="profile.edit.immutable.field"
             var="immutable_field"/>
<fmt:message bundle="${bundle}" key="profile.edit.button"
             var="try_create_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${page_title}</h3>
    </div>

    <div class="row justify-content-center py-2">
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
                <label class="text-muted" for="summary">${summary_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="summary" name="summary"
                       value="<c:if test="${init.summary[0] != null}">${init.summary[0]}</c:if><c:if test="${init.summary[0] == null}">${course.summary}</c:if>"/>
                <div class="text-danger small">${errors.summary}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="description">${description_lable}</label>
                <textarea class="form-control form-control-sm" id="description"
                          rows="4" style="resize: none"
                          name="description"><c:if
                        test="${init.description[0] != null}">${init.description[0]}</c:if><c:if
                        test="${init.description[0] == null}">${course.description}</c:if></textarea>
                <div class="text-danger small">${errors.description}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="startDate">${start_date_lable}</label>
                <input type="date" class="form-control form-control-sm"
                       id="startDate" name="startDate"
                       value="<c:if test="${init.startDate[0] != null}">${init.startDate[0]}</c:if><c:if test="${init.startDate[0] == null}">${course.startDate}</c:if>"/>
                <div class="text-danger small">${errors.startDate}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="endDate">${end_date_lable}</label>
                <input type="date" class="form-control form-control-sm"
                       id="endDate" name="endDate"
                       value="<c:if test="${init.endDate[0] != null}">${init.endDate[0]}</c:if><c:if test="${init.endDate[0] == null}">${course.endDate}</c:if>"/>
                <div class="text-danger small">${errors.endDate}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="studentsLimit">${students_limit_lable}</label>
                <input class="form-control" id="studentsLimit" type="number"
                       name="studentsLimit"
                       value="<c:if test="${init.studentsLimit[0] != null}">${init.studentsLimit[0]}</c:if><c:if test="${init.studentsLimit[0] == null}">${course.studentsLimit}</c:if>"/>
                <div class="text-danger small">${errors.studentsLimit}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted" for="status">${status_lable}</label>
                <select class="form-control form-control-sm" id="status"
                        name="status">
                    <option
                            <c:if test="${init.status[0] == 'NOT_STARTED'}">selected</c:if>
                            value="NOT_STARTED">${status_not_started}</option>
                    <option
                            <c:if test="${init.status[0] == 'IN_PROGRESS'}">selected</c:if>
                            value="IN_PROGRESS">${status_in_progress}</option>
                    <option
                            <c:if test="${init.status[0] == 'FINISHED'}">selected</c:if>
                            value="FINISHED">${status_finished}</option>
                </select>
                <div class="text-warning small">${immutable_field}</div>
            </div>

            <div class="row justify-content-center py-4">
                <button class="btn btn-outline-info"
                        type="submit">${try_create_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
