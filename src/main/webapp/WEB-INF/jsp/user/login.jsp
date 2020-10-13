<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="login" class="java.lang.String"/>

<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="message.registration.successes"
             var="registration_message"/>
<fmt:message bundle="${bundle}" key="login.summary" var="login_summary"/>
<fmt:message bundle="${bundle}" key="user.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="user.password" var="password_lable"/>
<fmt:message bundle="${bundle}" key="login.submit" var="login_button"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${login_summary}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-3" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="login"/>

            <c:if test="${param.get('isRegistrationOk')}">
                <div class="alert alert-success" role="alert">
                        ${registration_message}
                </div>
            </c:if>

            <%@include file="../component/alert_error.jsp" %>

            <div class="form-group row">
                <label class="text-muted" for="login">${login_lable}</label>
                <input type="text" class="form-control form-control-sm"
                       id="login" name="login" value="${init.login[0]}"/>
                <div class="text-danger small">${errors.login}</div>
            </div>

            <div class="form-group row">
                <label class="text-muted"
                       for="password">${password_lable}</label>
                <input class="form-control form-control-sm" id="password"
                       type="password" name="password"/>
                <div class="text-danger small">${errors.password}</div>
            </div>

            <div class="row justify-content-center py-4">
                <button class="btn btn-outline-info"
                        type="submit">${login_button}</button>
            </div>
        </form>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
