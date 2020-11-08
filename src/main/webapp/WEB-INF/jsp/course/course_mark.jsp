<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="init" type="java.util.Map"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Finish course</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="course.result.summary"
             var="mark_page_summary"/>
<fmt:message bundle="${bundle}" key="course.result.mark"
             var="mark_label"/>
<fmt:message bundle="${bundle}" key="course.result.comment"
             var="comment_label"/>
<fmt:message bundle="${bundle}" key="button.back" var="back_button"/>
<fmt:message bundle="${bundle}" key="button.save" var="save_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${mark_page_summary}</h3>
    </div>

    <div class="row py-5">
        <div class="container-fluid">
            <form class="form"
                  action="${pageContext.request.contextPath}/main">
                <input type="hidden" name="command" value="set_course_mark"/>
                <input type="hidden" name="courseId"
                       value="${param.get("courseId")}"/>
                <input type="hidden" name="userId"
                       value="${param.get("userId")}"/>

                <div class="form-group row">
                    <div class="container-fluid">
                        <label class="text-muted col-6"
                               for="mark">${mark_label} <span
                                class="text-danger">*</span></label>
                        <%--@elvariable id="courseResult" type="by.epamtc.courses.entity.CourseResult"--%>
                        <input class="form-control form-control-sm col-3"
                               id="mark" type="number" name="mark"
                               value="<c:if
                               test="${init.mark[0] != null}">${init.mark[0]}</c:if><c:if
                               test="${init.mark[0] == null}">${courseResult.mark}</c:if>"/>
                        <div class="text-danger small">${errors.mark}</div>

                        <label class="text-muted col-6"
                               for="comment">${comment_label} <span
                                class="text-danger">*</span></label>
                        <textarea class="form-control form-control-sm"
                                  id="comment" rows="4" style="resize: none"
                                  name="comment"><c:if
                                test="${init.comment[0] != null}">${init.comment[0]}</c:if><c:if
                                test="${init.comment[0] == null}">${courseResult.comment}</c:if></textarea>
                        <div class="text-danger small">${errors.comment}</div>
                    </div>
                </div>

                <a class="btn btn-outline-secondary mr-2"
                   href="${pageContext.request.contextPath}/main?command=get_course_details_page&courseId=${param.get("courseId")}">
                    ${back_button}
                </a>
                <input type="submit" class="btn btn-outline-info"
                       value="${save_button}"/>
            </form>
        </div>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
