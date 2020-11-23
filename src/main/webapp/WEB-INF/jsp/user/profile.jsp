<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Profile</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="profile.summary" var="profile_summary"/>
<fmt:message bundle="${bundle}" key="message.edit.successes"
             var="succsses_edit_message"/>
<fmt:message bundle="${bundle}" key="user.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="user.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="user.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="user.birthday" var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="user.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="user.role.student" var="role_student"/>
<fmt:message bundle="${bundle}" key="user.role.lecturer" var="role_lecturer"/>
<fmt:message bundle="${bundle}" key="button.edit" var="edit_button"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${profile_summary}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-7" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="get_edit_profile_page"/>

            <c:if test="${param.get('isUpdatingOk')}">
                <div class="alert alert-success" role="alert">
                        ${succsses_edit_message}
                </div>
            </c:if>

            <input type="hidden" name="id" value="${user.id}"/>

            <c:choose>
                <c:when test="${not empty user.photoPath}">
                    <div class="form-group row">
                        <img class="rounded mx-auto d-block w-50"
                             src="/uploadFiles/${user.id}/${user.photoPath}"
                             alt="User photo">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group row">
                        <img class="rounded mx-auto d-block w-50"
                             src="${pageContext.request.contextPath}/uploadFiles/default/defaultPhoto.png"
                             alt="User default photo">
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="form-group row justify-content-center">
                <label for="surname"
                       class="col-lg-3 col-form-label text-muted text-right">${surname_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="surname" value="${fn:escapeXml(user.surname)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="name"
                       class="col-lg-3 col-form-label text-muted text-right">${name_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="name" value="${fn:escapeXml(user.name)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="email"
                       class="col-lg-3 col-form-label text-muted text-right">${email_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="email" value="${fn:escapeXml(user.email)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="birthday"
                       class="col-lg-3 col-form-label text-muted text-right">${birthday_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="birthday"
                           value="${fn:escapeXml(user.birthday)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="role"
                       class="col-lg-3 col-form-label text-muted text-right">${role_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="role"
                           value="<c:if test="${user.role == 'STUDENT'}">${fn:escapeXml(role_student)}</c:if><c:if test="${user.role == 'LECTURER'}">${fn:escapeXml(role_lecturer)}</c:if>"/>
                </div>
            </div>

            <div class="row justify-content-center py-2">
                <button class="btn btn-outline-info"
                        type="submit">${edit_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
