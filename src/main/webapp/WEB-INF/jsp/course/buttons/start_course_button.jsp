<fmt:message bundle="${bundle}" key="course.details.button.startCourse"
             var="start_button"/>

<form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
      method="post">
    <input type="hidden" name="command" value="start_course"/>
    <input type="hidden" name="courseId" value="${course.id}"/>
    <button class="btn btn-outline-primary m-1" type="submit" name="status"
            value="IN_PROGRESS">${start_button}
    </button>
</form>