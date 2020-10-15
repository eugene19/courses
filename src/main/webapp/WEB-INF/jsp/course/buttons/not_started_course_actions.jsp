<%--@elvariable id="course" type="by.epamtc.courses.entity.Course"--%>
<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:message bundle="${bundle}" key="button.edit" var="edit_button"/>
<fmt:message bundle="${bundle}" key="course.details.button.startCourse"
             var="start_button"/>
<fmt:message bundle="${bundle}" key="button.enterOnCourse"
             var="apply_on_course_button"/>
<fmt:message bundle="${bundle}" key="button.leaveFromCourse" var="leave_btn"/>

<c:if test="${user.role == 'LECTURER' and course.lecturerId == user.id}">
    <a class="btn btn-outline-primary m-1"
       href="${pageContext.request.contextPath}/main?command=get_edit_course_page&courseId=${course.id}">
        <i class="fa fa-edit text-primary"></i> ${edit_button}
    </a>

    <form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
          method="post">
        <input type="hidden" name="command" value="start_course"/>
        <input type="hidden" name="courseId" value="${course.id}"/>
        <button class="btn btn-outline-primary m-1" type="submit"
                name="status"
                value="IN_PROGRESS">${start_button}
        </button>
    </form>
</c:if>

<c:if test="${user == null or (user.role == 'STUDENT' and userCourseStatus == null)}">
    <form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
          method="post">
        <input type="hidden" name="command" value="apply_on_course"/>
        <input type="hidden" name="courseId" value="${course.id}"/>

        <input class="btn btn-outline-primary m-1" type="submit"
               value="${apply_on_course_button}"/>
    </form>
</c:if>

<c:if test="${user.role == 'STUDENT' and userCourseStatus != null}">
    <form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
          method="post">
        <input type="hidden" name="command" value="leave_from_course"/>
        <input type="hidden" name="courseId" value="${course.id}"/>

        <input class="btn btn-outline-secondary m-1" type="submit"
               value="${leave_btn}"/>
    </form>
</c:if>
