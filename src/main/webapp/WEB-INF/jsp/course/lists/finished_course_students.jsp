<fmt:message bundle="${bundle}" key="course.details.button.setResult"
             var="set_result_button"/>
<fmt:message bundle="${bundle}" key="user.course.status.applied"
             var="applied_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.entered"
             var="entered_value"/>
<fmt:message bundle="${bundle}" key="user.course.status.notEntered"
             var="not_entered_value"/>

<c:forEach var="user_course" items="${usersOnCourse}">
    <div class="container-fluid">
        <form class="form" action="${pageContext.request.contextPath}/main">
            <div class="form-group row col-6">
                <p class="text-muted col-4">${user_course.key.surname} ${user_course.key.name}</p>
            </div>
        </form>
    </div>
</c:forEach>