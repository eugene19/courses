<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>
<%--@elvariable id="course" type="by.epamtc.courses.entity.Course"--%>
<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="init" type="java.util.Map"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Edit course</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="courses.edit.title" var="page_title"/>
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
<fmt:message bundle="${bundle}" key="course.status.inProgress"
             var="status_in_progress"/>
<fmt:message bundle="${bundle}" key="course.status.finished"
             var="status_finished"/>
<fmt:message bundle="${bundle}" key="button.save" var="create_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${page_title}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-4" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="edit_course"/>
            <input type="hidden" name="id"
                   value="<c:if test="${init.id[0] != null}">${init.id[0]}</c:if><c:if test="${init.id[0] == null}">${course.id}</c:if>"/>
            <input type="hidden" name="lecturerId" value="${user.id}"/>

            <%@include file="../component/alert_error.jsp" %>

            <div class="form-group row">
                <label class="text-muted" for="summary">${summary_lable} <span
                        class="text-danger">*</span></label>
                <input type="text" class="form-control form-control-sm"
                       id="summary" name="summary" required
                       value="<c:if test="${init.summary[0] != null}">${fn:escapeXml(init.summary[0])}</c:if><c:if test="${init.summary[0] == null}">${fn:escapeXml(course.summary)}</c:if>"
                       minlength="3" maxlength="100"/>
                <div class="text-danger small">${errors.summary}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="description">${description_lable} <span
                        class="text-danger">*</span></label>
                <textarea class="form-control form-control-sm" id="description"
                          rows="4" style="resize: none"
                          name="description" required minlength="3"
                          maxlength="1130"><c:if
                        test="${init.description[0] != null}">${fn:escapeXml(init.description[0])}</c:if><c:if
                        test="${init.description[0] == null}">${fn:escapeXml(course.description)}</c:if></textarea>
                <div class="text-danger small">${errors.description}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="startDate">${start_date_lable} <span
                        class="text-danger">*</span></label>
                <input type="date" class="form-control form-control-sm"
                       id="startDate" name="startDate" required
                       value="<c:if test="${init.startDate[0] != null}">${fn:escapeXml(init.startDate[0])}</c:if><c:if test="${init.startDate[0] == null}">${fn:escapeXml(course.startDate)}</c:if>"
                       max="2099-12-31"/>
                <div class="text-danger small">${errors.startDate}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="endDate">${end_date_lable} <span
                        class="text-danger">*</span></label>
                <input type="date" class="form-control form-control-sm"
                       id="endDate" name="endDate" required max="2099-12-31"
                       value="<c:if test="${init.endDate[0] != null}">${fn:escapeXml(init.endDate[0])}</c:if><c:if test="${init.endDate[0] == null}">${fn:escapeXml(course.endDate)}</c:if>"/>
                <div class="text-danger small">${errors.endDate}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="studentsLimit">${students_limit_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control" id="studentsLimit" type="number"
                       name="studentsLimit" required min="1" max="100"
                       value="<c:if test="${init.studentsLimit[0] != null}">${fn:escapeXml(init.studentsLimit[0])}</c:if><c:if test="${init.studentsLimit[0] == null}">${fn:escapeXml(course.studentsLimit)}</c:if>"/>
                <div class="text-danger small">${errors.studentsLimit}</div>
            </div>

            <div class="form-group row">
                <input type="hidden" name="status"
                       value="<c:if test="${init.status[0] != null}">${fn:escapeXml(init.status[0])}</c:if><c:if test="${init.status[0] == null}">${fn:escapeXml(course.status)}</c:if>"/>
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
