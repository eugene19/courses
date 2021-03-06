<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>
<html>
<head>
    <title>Courses</title>
    <%@include file="../component/link_style.jsp" %>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="message.create.successes"
             var="succsses_create_message"/>
<fmt:message bundle="${bundle}" key="message.edit.successes"
             var="succsses_edit_message"/>
<fmt:message bundle="${bundle}" key="courses.summary" var="courses_summary"/>
<fmt:message bundle="${bundle}" key="button.create" var="create_button"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${courses_summary}</h3>
    </div>

    <c:if test="${param.get('isCreationOk')}">
        <div class="alert alert-success" role="alert">
                ${succsses_create_message}
        </div>
    </c:if>

    <c:if test="${param.get('isUpdatingOk')}">
        <div class="alert alert-success" role="alert">
                ${succsses_edit_message}
        </div>
    </c:if>

    <%@include file="../component/alert_error.jsp" %>

    <div class="row">
        <div class="col-11">
            <%@include file="courses_filter.jsp" %>
        </div>
        <c:if test="${user.role == 'LECTURER'}">
            <div class="col-1">
                <a href="${pageContext.request.contextPath}/main?command=get_add_course_page">
                    <button class="btn btn-success">${create_button}</button>
                </a>
            </div>
        </c:if>
    </div>

    <%--@elvariable id="courseList" type="java.util.List"--%>
    <c:if test="${empty courseList}">
        <%@include file="../component/alert_empty_list.jsp" %>
    </c:if>

    <div class="row mb-3">
        <c:forEach var="course" items="${courseList}">
            <div class="col col-md-12 my-2 p-3  border border-light shadow-sm">
                <div class="media position-relative">
                    <div class="media-body">
                        <div class="row">
                            <div class="col-10 m-0">
                                <h5 class="mt-0">
                                    <myTag:cutLongText
                                            text="${fn:escapeXml(course.summary)}"
                                            maxLength="80"/>
                                </h5>
                            </div>
                            <div class="col-2 m-0 text-right">
                                <c:choose>
                                    <c:when test="${course.status == 'NOT_STARTED'}">
                                        <%@include
                                                file="statuses/not_started.jsp" %>
                                    </c:when>
                                    <c:when test="${course.status == 'IN_PROGRESS'}">
                                        <%@include
                                                file="statuses/in_progress.jsp" %>
                                    </c:when>
                                    <c:when test="${course.status == 'FINISHED'}">
                                        <%@include
                                                file="statuses/finished.jsp" %>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>

                        <p>
                            <myTag:cutLongText
                                    text="${fn:escapeXml(course.description)}"
                                    maxLength="300"/>
                        </p>
                        <p class="text-muted small">
                                ${course.startDate} - ${course.endDate}
                        </p>
                        <a href="${pageContext.request.contextPath}/main?command=get_course_details_page&courseId=${course.id}"
                           class="stretched-link small"></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <%@include file="buttons/pagination.jsp" %>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>