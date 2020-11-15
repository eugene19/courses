<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="init" type="java.util.Map"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Add course</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="courses.add.title" var="page_title"/>
<fmt:message bundle="${bundle}" key="course.summary" var="summary_lable"/>
<fmt:message bundle="${bundle}" key="course.description"
             var="description_lable"/>
<fmt:message bundle="${bundle}" key="course.startDate"
             var="start_date_lable"/>
<fmt:message bundle="${bundle}" key="course.endDate" var="end_date_lable"/>
<fmt:message bundle="${bundle}" key="course.studentsLimit"
             var="students_limit_lable"/>
<fmt:message bundle="${bundle}" key="course.status"
             var="status_lable"/>
<fmt:message bundle="${bundle}" key="course.status.notStarted"
             var="status_not_started"/>
<fmt:message bundle="${bundle}" key="error.fieldImmutable"
             var="immutable_field"/>
<fmt:message bundle="${bundle}" key="button.create"
             var="create_button"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${page_title}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-4" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="add_course"/>
            <input type="hidden" name="lecturerId" value="${user.id}"/>
            <input type="hidden" name="status" value="NOT_STARTED"/>

            <%@include file="../component/alert_error.jsp" %>

            <div class="form-group row">
                <label class="text-muted" for="summary">${summary_lable} <span
                        class="text-danger">*</span></label>
                <input type="text" class="form-control form-control-sm"
                       id="summary" name="summary" value="${init.summary[0]}"
                       required minlength="3" maxlength="100"/>
                <div class="text-danger small">${errors.summary}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="description">${description_lable} <span
                        class="text-danger">*</span></label>
                <textarea class="form-control form-control-sm" id="description"
                          rows="4" style="resize: none"
                          name="description" required minlength="3"
                          maxlength="1130">${init.description[0]}</textarea>
                <div class="text-danger small">${errors.description}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="startDate">${start_date_lable} <span
                        class="text-danger">*</span></label>
                <input type="date" class="form-control form-control-sm"
                       id="startDate" name="startDate" required
                       value="${init.startDate[0]}"/>
                <div class="text-danger small">${errors.startDate}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="endDate">${end_date_lable} <span
                        class="text-danger">*</span></label>
                <input type="date" class="form-control form-control-sm"
                       id="endDate" name="endDate" value="${init.endDate[0]}"
                       required max="2099-12-31"/>
                <span class="text-danger small">${errors.endDate}</span>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="studentsLimit">${students_limit_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control form-control-sm" id="studentsLimit"
                       type="number" name="studentsLimit"
                       value="${init.studentsLimit[0]}"
                       required min="1" max="100"/>
                <div class="text-danger small">${errors.studentsLimit}</div>
            </div>

            <div class="row justify-content-center py-4">
                <button class="btn btn-outline-info"
                        type="submit">${create_button}</button>
            </div>
        </form>
        <%@include file="../component/field_validation.jsp" %>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
