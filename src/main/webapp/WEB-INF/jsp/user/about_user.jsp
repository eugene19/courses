<%--@elvariable id="foundUser" type="by.epamtc.courses.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>User's info</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="profile.summary" var="profile_summary"/>
<fmt:message bundle="${bundle}" key="user.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="user.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="user.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="user.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="user.birthday" var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="user.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="user.role.student" var="role_student"/>
<fmt:message bundle="${bundle}" key="user.role.lecturer" var="role_lecturer"/>
<fmt:message bundle="${bundle}" key="menu.account.marks"
             var="student_result_summary"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${profile_summary}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <div class="col-md-7">
            <c:choose>
                <c:when test="${not empty foundUser.photoPath}">
                    <div class="form-group row">
                        <img class="rounded mx-auto d-block w-50"
                             src="/uploadFiles/${foundUser.id}/${foundUser.photoPath}"
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
                           id="surname"
                           value="${fn:escapeXml(foundUser.surname)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="name"
                       class="col-lg-3 col-form-label text-muted text-right">${name_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="name" value="${fn:escapeXml(foundUser.name)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="email"
                       class="col-lg-3 col-form-label text-muted text-right">${email_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="email" value="${fn:escapeXml(foundUser.email)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="birthday"
                       class="col-lg-3 col-form-label text-muted text-right">${birthday_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="birthday"
                           value="${fn:escapeXml(foundUser.birthday)}"/>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="role"
                       class="col-lg-3 col-form-label text-muted text-right">${role_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="role"
                           value="<c:if test="${foundUser.role == 'STUDENT'}">${role_student}</c:if><c:if test="${foundUser.role == 'LECTURER'}">${role_lecturer}</c:if>"/>
                </div>
            </div>

            <c:if test="${foundUser.role == 'STUDENT'}">
                <div class="row justify-content-center py-5">
                    <h3 class="h3 mb-3 font-weight-normal">${student_result_summary}</h3>
                </div>

                <%@include file="student_results_table.jsp" %>
            </c:if>
        </div>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
