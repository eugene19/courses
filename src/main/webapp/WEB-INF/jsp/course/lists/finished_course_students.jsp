<c:forEach var="user_course" items="${usersOnCourse}">
    <div class="container-fluid">
        <form class="form" action="${pageContext.request.contextPath}/main">
            <div class="form-group row col-6">
                <p class="text-muted col-4">
                    <a class="text-info"
                       href="${pageContext.request.contextPath}/main?command=get_about_user_page&userId=${user_course.key.id}">${user_course.key.surname} ${user_course.key.name}
                    </a>
                </p>
            </div>
        </form>
    </div>
</c:forEach>