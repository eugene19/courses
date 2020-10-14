<fmt:message bundle="${bundle}" key="user.course.status.applied"
             var="applied_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.entered"
             var="entered_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.notEntered"
             var="not_entered_value"/>

<c:forEach var="user_course" items="${usersOnCourse}">
    <div class="container-fluid">
        <form class="form"
              action="${pageContext.request.contextPath}/main">
            <input type="hidden" name="command"
                   value="update_user_on_course_status"/>
            <input type="hidden" name="courseId" value="${course.id}"/>
            <input type="hidden" name="userId" value="${user_course.key.id}"/>

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
            </div>
        </form>
    </div>
</c:forEach>