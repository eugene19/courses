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

<fmt:message bundle="${bundle}" key="profile.edit.summary"
             var="edit_profile_summary"/>
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
<fmt:message bundle="${bundle}" key="profile.edit.button"
             var="save_profile_button"/>

<div class="container">
    <div class="row align-items-center justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${edit_profile_summary}</h3>
    </div>

    <div class="row align-items-center justify-content-center py-2">
        <form class="col-md-6" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="edit_profile">

            <div class="form-group row">
                <label for="surname"
                       class="col-lg-2 col-form-label text-muted">${surname_lable}</label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="surname" name="surname"
                           value="<c:if test="${init.surname[0] != null}">${init.surname[0]}</c:if><c:if test="${init.surname[0] == null}">${user.surname}</c:if>">
                    <span class="text-danger small col-lg-11">${errors.surname} </span>
                </div>

            </div>

            <div class="form-group row">
                <label for="name"
                       class="col-lg-2 col-form-label text-muted">${name_lable}</label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="name" name="name"
                           value="<c:if test="${init.name[0] != null}">${init.name[0]}</c:if><c:if test="${init.name[0] == null}">${user.name}</c:if>">
                    <span class="text-danger small col-lg-11">${errors.name} </span>
                </div>
            </div>

            <div class="form-group row">
                <label for="email"
                       class="col-lg-2 col-form-label text-muted">${email_lable}</label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="email" name="email"
                           value="<c:if test="${init.email[0] != null}">${init.email[0]}</c:if><c:if test="${init.email[0] == null}">${user.email}</c:if>">
                    <span class="text-danger small col-lg-11">${errors.email} </span>
                </div>
            </div>

            <div class="form-group row">
                <label for="birthday"
                       class="col-lg-2 col-form-label text-muted">${birthday_lable}</label>
                <div class="col-lg-6">
                    <input type="date" class="form-control"
                           id="birthday" name="birthday"
                           value="<c:if test="${init.birthday[0] != null}">${init.birthday[0]}</c:if><c:if test="${init.birthday[0] == null}">${user.birthday}</c:if>">
                    <span class="text-danger small col-lg-11">${errors.birthday} </span>
                </div>
            </div>

            <div class="form-group row">
                <label for="role"
                       class="col-lg-2 col-form-label text-muted">${role_lable}</label>
                <div class="col-lg-6">
                    <select class="form-control" id="role"
                            name="role">
                        <c:choose>
                            <c:when test="${init.role[0] != null}">
                                <option
                                        <c:if test="${init.role[0]  == 'STUDENT'}">selected</c:if>
                                        value="STUDENT">${role_student}</option>
                                <option
                                        <c:if test="${init.role[0] == 'LECTURER'}">selected</c:if>
                                        value="LECTURER">${role_lecturer}</option>
                            </c:when>
                            <c:otherwise>
                                <option
                                        <c:if test="${user.role  == 'STUDENT'}">selected</c:if>
                                        value="STUDENT">${role_student}</option>
                                <option
                                        <c:if test="${user.role == 'LECTURER'}">selected</c:if>
                                        value="LECTURER">${role_lecturer}</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <span class="text-danger small col-lg-11">${errors.role} </span>
                </div>
            </div>

            <div class="row align-items-center justify-content-center py-2">
                <button class="btn btn-outline-info"
                        type="submit">${save_profile_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
