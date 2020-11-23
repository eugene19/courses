<%--@elvariable id="coursesWithResult" type="java.util.Map"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>

<fmt:message bundle="${bundle}" key="menu.courses" var="course_th"/>
<fmt:message bundle="${bundle}" key="course.result.mark" var="course_mark_th"/>
<fmt:message bundle="${bundle}" key="course.result.comment"
             var="course_comment_th"/>

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
                                text="${fn:escapeXml(courseResult.key.summary)}"
                                maxLength="50"/></a>
                    </td>
                    <td>${fn:escapeXml(courseResult.value.mark)}</td>
                    <td>${fn:escapeXml(courseResult.value.comment)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>