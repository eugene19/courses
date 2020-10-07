<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="profile.summary" var="profile_summary"/>
<fmt:message bundle="${bundle}" key="profile.edit..message.success"
             var="succsses_edit_message"/>
<fmt:message bundle="${bundle}" key="registration.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="registration.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="registration.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="registration.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="registration.birthday"
             var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="registration.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="registration.role.student"
             var="role_student"/>
<fmt:message bundle="${bundle}" key="registration.role.lecturer"
             var="role_lecturer"/>
<fmt:message bundle="${bundle}" key="profile.button.edit"
             var="edit_profile_button"/>

<div class="container">
    <div class="row align-items-center justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${profile_summary}</h3>
    </div>

    <div class="row align-items-center justify-content-center py-2">
        <form class="col-md-6" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="get_edit_profile_page">

            <c:if test="${param.get('isUpdatingOk')}">
                <div class="alert alert-success" role="alert">
                        ${succsses_edit_message}
                </div>
            </c:if>

            <input type="hidden" name="id" value="${user.id}">

            <div class="form-group row">
                <label for="surname"
                       class="col-lg-2 col-form-label text-muted">${surname_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="surname" value="${user.surname}">
                </div>
            </div>

            <div class="form-group row">
                <label for="name"
                       class="col-lg-2 col-form-label text-muted">${name_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="name" value="${user.name}">
                </div>
            </div>

            <div class="form-group row">
                <label for="email"
                       class="col-lg-2 col-form-label text-muted">${email_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="email" value="${user.email}">
                </div>
            </div>

            <div class="form-group row">
                <label for="birthday"
                       class="col-lg-2 col-form-label text-muted">${birthday_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="birthday" value="${user.birthday}">
                </div>
            </div>

            <div class="form-group row">
                <label for="role"
                       class="col-lg-2 col-form-label text-muted">${role_lable}</label>
                <div class="col-lg-6">
                    <input type="text" readonly class="form-control-plaintext"
                           id="role"
                           value="<c:if test="${user.role == 'STUDENT'}">${role_student}</c:if><c:if test="${user.role == 'LECTURER'}">${role_lecturer}</c:if>">
                </div>
            </div>

            <div class="row align-items-center justify-content-center py-2">
                <button class="btn btn-outline-info"
                        type="submit">${edit_profile_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
