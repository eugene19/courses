<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>

<html>
<head>
    <title>Student results</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>
<fmt:message bundle="${bundle}" key="menu.account.marks"
             var="student_result_summary"/>
<fmt:message bundle="${bundle}" key="menu.courses" var="course_th"/>
<fmt:message bundle="${bundle}" key="course.result.mark" var="course_mark_th"/>
<fmt:message bundle="${bundle}" key="course.result.comment"
             var="course_comment_th"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${student_result_summary}</h3>
    </div>

    <div class="row justify-content-center pt-2 pb-5">
        <%--@elvariable id="coursesWithResult" type="java.util.Map"--%>
        <c:choose>
            <c:when test="${empty coursesWithResult}">
                <%@include file="../component/alert_empty_list.jsp" %>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">${course_th}</th>
                        <th scope="col">${course_mark_th}</th>
                        <th scope="col">${course_comment_th}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${coursesWithResult}" var="courseResult"
                               varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>
                                <a class="text-info"
                                   href="${pageContext.request.contextPath}/main?command=get_course_details_page&courseId=${courseResult.key.id}"><myTag:cutLongText
                                        text="${courseResult.key.summary}"
                                        maxLength="50"/></a>
                            </td>
                            <td>${courseResult.value.mark}</td>
                            <td>${courseResult.value.comment}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
