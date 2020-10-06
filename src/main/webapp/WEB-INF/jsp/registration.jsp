<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="registration.summary"
             var="registration_summary"/>
<fmt:message bundle="${bundle}" key="registration.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="registration.password"
             var="password_lable"/>
<fmt:message bundle="${bundle}" key="registration.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="registration.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="registration.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="registration.birthday"
             var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="registration.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="registration.submit"
             var="try_register_button"/>
<fmt:message bundle="${bundle}" key="registration.role.student"
             var="role_student"/>
<fmt:message bundle="${bundle}" key="registration.role.lecturer"
             var="role_lecturer"/>

<div class="container">
    <div class="row align-items-center justify-content-center py-5">
        <form action="${pageContext.request.contextPath}/main" method="post">
            <input type="hidden" name="command" value="registration"/>

            <h3 class="h3 mb-3 font-weight-normal">${registration_summary}</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </c:if>

            <div class="form-group row">
                <label for="login">${login_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="login" name="login"
                       value="${init.login[0]}"/>
                <span class="text-danger small">${errors.login} </span>
            </div>

            <div class="form-group row">
                <label for="password">${password_lable}</label>
                <input class="form-control form-control-sm" id="password"
                       type="password"
                       name="password"/>
                <div class="text-danger small">${errors.password} </div>
            </div>

            <div class="form-group row">
                <label for="surname">${surname_lable}</label>
                <input class="form-control form-control-sm" id="surname"
                       type="text"
                       name="surname" value="${init.surname[0]}"/>
                <div class="text-danger small">${errors.surname} </div>
            </div>

            <div class="form-group row">
                <label for="name">${name_lable}</label>
                <input class="form-control form-control-sm" id="name"
                       type="text"
                       name="name"
                       value="${init.name[0]}"/>
                <div class="text-danger small">${errors.name} </div>
            </div>

            <div class="form-group row">
                <label for="email">${email_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="email" name="email"
                       value="${init.email[0]}"/>
                <span class="text-danger small">${errors.email} </span>
            </div>
            <div class="form-group row">
                <label for="birthday">${birthday_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="birthday"
                       name="birthday" value="${init.birthday[0]}"/>
                <span class="text-danger small">${errors.birthday} </span>
            </div>

            <div class="form-group row">
                <label for="role">${role_lable}</label>
                <select class="form-control form-control-sm" id="role"
                        name="role">
                    <option
                            <c:if test="${init.role[0] == 'STUDENT'}">selected</c:if>
                            value="STUDENT">${role_student}</option>
                    <option
                            <c:if test="${init.role[0] == 'LECTURER'}">selected</c:if>
                            value="LECTURER">${role_lecturer}</option>
                </select>
                <span class="text-danger small">${errors.role} </span>
            </div>

            <button class="btn btn-outline-info"
                    type="submit">${try_register_button}</button>
        </form>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
