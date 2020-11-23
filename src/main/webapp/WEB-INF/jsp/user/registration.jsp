<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="init" type="java.util.Map"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Registration</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="registration.summary"
             var="register_summary"/>
<fmt:message bundle="${bundle}" key="user.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="user.password" var="password_lable"/>
<fmt:message bundle="${bundle}" key="user.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="user.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="user.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="user.birthday" var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="user.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="user.role.student" var="role_student"/>
<fmt:message bundle="${bundle}" key="user.role.lecturer" var="role_lecturer"/>
<fmt:message bundle="${bundle}" key="registration.submit" var="register_btn"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${register_summary}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-3" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="registration"/>

            <%@include file="../component/alert_error.jsp" %>

            <div class="form-group row">
                <label class="text-muted" for="login">${login_lable} <span
                        class="text-danger">*</span></label>
                <input type="text" class="form-control form-control-sm"
                       id="login" name="login"
                       value="${fn:escapeXml(init.login[0])}" required
                       pattern="\w{3,15}" minlength="3" maxlength="15"/>
                <div class="text-danger small">${errors.login}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="password">${password_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control form-control-sm" id="password"
                       type="password" name="password" required
                       pattern="\w{3,15}" minlength="3" maxlength="15"/>
                <div class="text-danger small">${errors.password}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted" for="surname">${surname_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control form-control-sm" id="surname"
                       type="text" name="surname"
                       value="${fn:escapeXml(init.surname[0])}"
                       required pattern="[A-Za-zА-Яа-яЁё]{2,15}" minlength="2"
                       maxlength="15"/>
                <div class="text-danger small">${errors.surname}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted" for="name">${name_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control form-control-sm" id="name"
                       type="text" name="name"
                       value="${fn:escapeXml(init.name[0])}" required
                       pattern="[A-Za-zА-Яа-яЁё]{2,15}" minlength="2"
                       maxlength="15"/>
                <div class="text-danger small">${errors.name}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted" for="email">${email_lable} <span
                        class="text-danger">*</span></label>
                <input class="form-control form-control-sm" type="text"
                       id="email" name="email" required
                       value="${fn:escapeXml(init.email[0])}"
                       pattern="\w{3,15}@[A-Za-z]{3,15}\.[A-Za-z]{1,4}"
                       minlength="9" maxlength="45"/>
                <div class="text-danger small">${errors.email}</div>
            </div>

            <jsp:useBean id="currentDate" class="java.util.Date"/>

            <div class="form-group row">
                <label class="text-muted"
                       for="birthday">${birthday_lable} <span
                        class="text-danger">*</span></label>
                <input type="date" class="form-control form-control-sm"
                       id="birthday" name="birthday"
                       value="${fn:escapeXml(init.birthday[0])}" required
                       min="1900-01-01"
                       max="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>"/>
                <div class="text-danger small">${errors.birthday}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted" for="role">${role_lable} <span
                        class="text-danger">*</span></label>
                <select class="form-control form-control-sm" id="role"
                        name="role" required>
                    <option
                            <c:if test="${init.role[0] == 'STUDENT'}">selected</c:if>
                            value="STUDENT">${role_student}</option>
                    <option
                            <c:if test="${init.role[0] == 'LECTURER'}">selected</c:if>
                            value="LECTURER">${role_lecturer}</option>
                </select>
                <div class="text-danger small">${errors.role}</div>
            </div>

            <div class="row justify-content-center py-4">
                <button class="btn btn-outline-info"
                        type="submit">${register_btn}</button>
            </div>
        </form>
        <%@include file="../component/field_validation.jsp" %>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
