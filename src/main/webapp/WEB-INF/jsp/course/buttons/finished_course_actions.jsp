<%--@elvariable id="course" type="by.epamtc.courses.entity.Course"--%>
<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:message bundle="${bundle}" key="button.getMaterials" var="materials_btn"/>

<c:if test="${userCourseStatus == 'ENTERED' or course.lecturerId == user.id}">
    <form class="m-0 p-0" action="${pageContext.request.contextPath}/main"
          method="post">
        <input type="hidden" name="command" value="get_course_materials"/>
        <input type="hidden" name="courseId" value="${course.id}"/>
        <input type="hidden" name="lecturerId" value="${course.lecturerId}"/>
        <input type="hidden" name="materials" value="${course.materialPath}"/>

        <input class="btn btn-outline-primary m-1" type="submit"
               value="${materials_btn}"/>
    </form>
</c:if>
