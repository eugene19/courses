<fmt:message bundle="${bundle}" key="course.details.button.setResult"
             var="set_result_button"/>

<c:forEach var="user_course" items="${usersOnCourse}">
    <div class="container-fluid">
        <form class="form" action="${pageContext.request.contextPath}/main">
            <div class="form-group row col-6">
                <p class="text-muted col-4">${user_course.key.surname} ${user_course.key.name}</p>

                <a class="col-4"
                   href="${pageContext.request.contextPath}/main?command=get_course_mark_page&userId=${user_course.key.id}&courseId=${course.id}">
                        ${set_result_button}
                </a>
            </div>
        </form>
    </div>
</c:forEach>