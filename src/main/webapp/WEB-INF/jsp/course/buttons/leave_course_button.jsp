<fmt:message bundle="${bundle}" key="button.leaveFromCourse" var="leave_btn"/>
<form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
      method="post">
    <input type="hidden" name="command" value="leave_from_course"/>
    <input type="hidden" name="courseId" value="${course.id}"/>

    <input class="btn btn-outline-secondary" type="submit"
           value="${leave_btn}"/>
</form>