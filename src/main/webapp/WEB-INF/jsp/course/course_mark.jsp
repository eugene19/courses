<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Finish course</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="course.details.summary"
             var="mark_page_summary"/>
<fmt:message bundle="${bundle}" key="button.save" var="save_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${mark_page_summary}</h3>
    </div>

    <div class="row py-5">
        <div class="container-fluid">
            <form class="form"
                  action="${pageContext.request.contextPath}/main">
                <input type="hidden" name="command" value="set_course_mark"/>
                <input type="hidden" name="courseId"
                       value="${param.get("courseId")}"/>
                <input type="hidden" name="userId" value="${student.id}"/>

                <div class="form-group row">
                    <label class="text-muted col-12">${student.surname} ${student.name}</label>

                    <div class="container-fluid">
                        <label class="text-muted col-6" for="mark">Mark</label>
                        <input class="form-control form-control-sm col-3"
                               id="mark" type="number" name="mark"
                               value="${init.mark[0]}"/>
                        <div class="text-danger small">${errors.mark}</div>

                        <label class="text-muted col-6"
                               for="comment">Comment</label>
                        <textarea class="form-control form-control-sm"
                                  id="comment" rows="4" style="resize: none"
                                  name="comment">${init.comment[0]}</textarea>
                        <div class="text-danger small">${errors.comment}</div>
                    </div>
                </div>

                <input type="submit" class="btn btn-outline-primary"
                       value="${save_button}"/>
            </form>
        </div>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>