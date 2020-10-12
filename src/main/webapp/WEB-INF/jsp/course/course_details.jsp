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
<fmt:message bundle="${bundle}" key="message.enter.send.successes"
             var="enter_send_message"/>
<fmt:message bundle="${bundle}" key="button.edit" var="edit_button"/>
<fmt:message bundle="${bundle}" key="button.enterOnCourse"
             var="enter_on_course_button"/>
<fmt:message bundle="${bundle}" key="button.leaveFromCourse"
             var="leave_course_button"/>
<fmt:message bundle="${bundle}" key="course.details.students.summary"
             var="students_list_summary"/>
<fmt:message bundle="${bundle}" key="user.course.status.applied"
             var="applied_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.entered"
             var="entered_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.notEntered"
             var="not_entered_value"/>
<fmt:message bundle="${bundle}" key="message.edit.successes"
             var="succsses_edit_message"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${details_summary}</h3>
    </div>

    <c:if test="${param.get('isSentOk')}">
        <div class="alert alert-success" role="alert">
                ${enter_send_message}
        </div>
    </c:if>

    <c:if test="${param.get('isUpdatingOk')}">
        <div class="alert alert-success" role="alert">
                ${succsses_edit_message}
        </div>
    </c:if>

    <%--    Добавить сообщение с ошибкой если isUpdating=false--%>

    <div class="row ml-2">
        <c:choose>
            <c:when test="${course.lecturerId == user.id}">
                <a class="btn btn-outline-primary m-1"
                   href="${pageContext.request.contextPath}/main?command=get_edit_course_page&courseId=${course.id}">
                    <i class="fa fa-edit text-primary"></i> ${edit_button}
                </a>
                <form class="m-0 p-0"
                      action="${pageContext.request.contextPath}/main"
                      method="post">
                    <input type="hidden" name="command"
                           value="update_course_status"/>
                    <input type="hidden" name="courseId"
                           value="${course.id}"/>

                    <c:choose>
                        <c:when test="${course.status == 'NOT_STARTED'}">
                            <button class="btn btn-outline-primary m-1"
                                    type="submit" name="status"
                                    value="IN_PROGRESS">Start
                            </button>
                        </c:when>
                        <c:when test="${course.status == 'IN_PROGRESS'}">
                            <a class="btn btn-outline-primary m-1"
                               href="${pageContext.request.contextPath}/main?command=get_finish_course_page&courseId=${course.id}">
                                Finish
                            </a>
                        </c:when>
                    </c:choose>
                </form>
            </c:when>
            <c:otherwise>
                <c:if test="${userCourseStatus == null}">
                    <form class="m-0 p-0"
                          action="${pageContext.request.contextPath}/main"
                          method="post">
                        <input type="hidden" name="command"
                               value="enter_on_course"/>
                        <input type="hidden" name="courseId"
                               value="${course.id}"/>

                        <input class="btn btn-outline-primary" type="submit"
                               value="${enter_on_course_button}"/>
                    </form>
                </c:if>

                <c:if test="${userCourseStatus != null}">
                    <form class="m-0 p-0"
                          action="${pageContext.request.contextPath}/main"
                          method="post">
                        <input type="hidden" name="command"
                               value="leave_from_course"/>
                        <input type="hidden" name="courseId"
                               value="${course.id}"/>

                        <input class="btn btn-outline-secondary" type="submit"
                               value="${leave_course_button}"/>
                    </form>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="row py-5">
        <div class="container-fluid">
            <h5 class="h5 mb-5 font-weight-normal">${course.summary}</h5>
        </div>
        <div class="container-fluid">
            ${course.description}
        </div>
    </div>

    <c:if test="${user.role == 'LECTURER' and course.lecturerId == user.id}">
        <div class="row py-5">
            <div class="container-fluid">
                <h5 class="h5 mb-5 font-weight-normal">${students_list_summary}</h5>
            </div>
            <jsp:useBean id="usersOnCourse" scope="request"
                         type="java.util.Map"/>
            <c:forEach var="user_course" items="${usersOnCourse}">
                <div class="container-fluid">
                    <form class="form"
                          action="${pageContext.request.contextPath}/main">
                        <input type="hidden" name="command"
                               value="update_user_on_course_status"/>
                        <input type="hidden" name="courseId"
                               value="${course.id}"/>
                        <input type="hidden" name="userId"
                               value="${user_course.key.id}"/>
                        <div class="form-group row col-6">
                            <label class="text-muted col-4"
                                   for="userCourseStatus">${user_course.key.surname} ${user_course.key.name}</label>
                            <select class="form-control form-control-sm col-4"
                                    id="userCourseStatus"
                                    name="userCourseStatus"
                                    onchange="submit()">
                                <option
                                        <c:if test="${user_course.value == 'APPLIED'}">selected</c:if>
                                        value="APPLIED">${applied_value}</option>
                                <option
                                        <c:if test="${user_course.value == 'ENTERED'}">selected</c:if>
                                        value="ENTERED">${entered_value}</option>
                                <option
                                        <c:if test="${user_course.value == 'NOT_ENTERED'}">selected</c:if>
                                        value="NOT_ENTERED">${not_entered_value}</option>
                            </select>
                            <c:if test="${user_course.value == 'ENTERED'}">
                                <a class="col-4"
                                   href="${pageContext.request.contextPath}/main?command=get_course_mark_page&userId=${user_course.key.id}&courseId=${course.id}">
                                    Set mark
                                </a>
                            </c:if>
                        </div>
                    </form>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>