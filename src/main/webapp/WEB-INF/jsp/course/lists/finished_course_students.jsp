<c:forEach var="user_course" items="${usersOnCourse}">
    <div class="container-fluid">
        <form class="form" action="${pageContext.request.contextPath}/main">
            <div class="form-group row col-6">
                <p class="text-muted col-4">${user_course.key.surname} ${user_course.key.name}</p>
            </div>
        </form>
    </div>
</c:forEach>