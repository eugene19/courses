<%--@elvariable id="course" type="by.epamtc.courses.entity.Course"--%>
<%--@elvariable id="usersOnCourse" type="java.util.List"--%>
<%--@elvariable id="userCourseStatus" type="by.epamtc.courses.entity.UserCourseStatus"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Course details</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('.spoiler-title').click(function () {
                $(this).parent().children('.spoiler-body').slideToggle(100);
                return false;
            });
        });
    </script>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="course.details.summary"
             var="details_summary"/>
<fmt:message bundle="${bundle}" key="message.enter.send.successes"
             var="enter_send_message"/>
<fmt:message bundle="${bundle}" key="course.details.students.summary"
             var="students_list_summary"/>
<fmt:message bundle="${bundle}" key="message.edit.successes"
             var="succsses_edit_message"/>
<fmt:message bundle="${bundle}" key="user.course.isApplied"
             var="user_is_applied"/>
<fmt:message bundle="${bundle}" key="user.course.isEntered"
             var="user_is_entered"/>
<fmt:message bundle="${bundle}" key="user.course.isNotEntered"
             var="user_is_not_entered"/>
<fmt:message bundle="${bundle}" key="user.error.fileIsEmpty"
             var="file_unchosen"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${details_summary}</h3>
    </div>

    <c:if test="${param.get('isUpdatingOk')}">
        <div class="alert alert-success" role="alert">
                ${succsses_edit_message}
        </div>
    </c:if>

    <c:if test="${param.get('isUpdatingOk') != null and !param.get('isUpdatingOk')}">
        <div class="alert alert-danger" role="alert">
                ${file_unchosen}
        </div>
    </c:if>

    <%@include file="../component/alert_error.jsp" %>

    <div class="row ml-1">
        <%-- Actions for course--%>
        <div class="col-10 pl-0">
            <div class="row">
                <c:if test="${course.status == 'NOT_STARTED'}">
                    <%@include file="buttons/not_started_course_actions.jsp" %>
                </c:if>

                <c:if test="${course.status == 'IN_PROGRESS'}">
                    <%@include file="buttons/in_progress_course_actions.jsp" %>
                </c:if>

                <c:if test="${course.status == 'FINISHED'}">
                    <%@include file="buttons/finished_course_actions.jsp" %>
                </c:if>
            </div>

            <c:if test="${course.status == 'NOT_STARTED' and user.id == course.lecturerId}">
                <%@include file="upload_materials_form.jsp" %>
            </c:if>
        </div>

        <c:if test="${userCourseStatus != null}">
            <div class="col-2 text-right small">
                <c:choose>
                    <c:when test="${userCourseStatus == 'APPLIED'}">
                        <p class="text-info font-weight-bold">${user_is_applied}</p>
                    </c:when>
                    <c:when test="${userCourseStatus == 'ENTERED'}">
                        <p class="text-success font-weight-bold">${user_is_entered}</p>
                    </c:when>
                    <c:when test="${userCourseStatus == 'NOT_ENTERED'}">
                        <p class="text-warning font-weight-bold">${user_is_not_entered}</p>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="row py-5">
        <div class="container-fluid">
            <h5 class="h5 mb-5 font-weight-normal pl-5">${course.summary}</h5>
        </div>
        <div class="container-fluid pl-4">
            ${course.description}
        </div>
    </div>
    <div class="row py-5">
        <div class="container-fluid pl-4">
            <fmt:message bundle="${bundle}" key="user.role.lecturer"
                         var="lecturer_summary"/>
            <div class="container-fluid">
                <h5 class="h5 mb-5 font-weight-normal pl-5">${lecturer_summary}</h5>
            </div>
            <div class="container-fluid">
                <div class="card" style="width: 18rem;">
                    <%--@elvariable id="lecturer" type="by.epamtc.courses.entity.User"--%>
                    <c:choose>
                        <c:when test="${not empty lecturer.photoPath}">
                            <img class="card-img-top rounded mx-auto d-block w-100"
                                 src="/uploadFiles/${lecturer.id}/${lecturer.photoPath}"
                                 alt="User photo">
                        </c:when>
                        <c:otherwise>
                            <img class="card-img-top rounded mx-auto d-block w-50"
                                 src="${pageContext.request.contextPath}/uploadFiles/default/defaultPhoto.png"
                                 alt="User default photo">
                        </c:otherwise>
                    </c:choose>
                    <div class="card-body">
                        <fmt:message bundle="${bundle}" key="user.itsMe"
                                     var="its_me"/>
                        <p class="card-text">${lecturer.surname} ${lecturer.name}
                            <c:if test="${user.id == lecturer.id}">(${its_me})</c:if></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${course.lecturerId == user.id}">
        <div class="row py-5">
            <div class="container-fluid">
                <h5 class="h5 mb-5 font-weight-normal pl-5">${students_list_summary}</h5>
            </div>

            <c:if test="${empty usersOnCourse}">
                <%@include file="../component/alert_empty_list.jsp" %>
            </c:if>

            <c:choose>
                <c:when test="${course.status == 'NOT_STARTED'}">
                    <%@ include file="lists/not_started_course_students.jsp" %>
                </c:when>
                <c:when test="${course.status == 'IN_PROGRESS'}">
                    <%@ include file="lists/in_progress_course_students.jsp" %>
                </c:when>
                <c:when test="${course.status == 'FINISHED'}">
                    <%@ include file="lists/finished_course_students.jsp" %>
                </c:when>
            </c:choose>
        </div>
    </c:if>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
