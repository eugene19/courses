<fmt:message bundle="${bundle}" key="button.enterOnCourse"
             var="enter_on_course_button"/>

<form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
      method="post">
    <input type="hidden" name="command" value="enter_on_course"/>
    <input type="hidden" name="courseId" value="${course.id}"/>

    <input class="btn btn-outline-primary" type="submit"
           value="${enter_on_course_button}"/>
</form>