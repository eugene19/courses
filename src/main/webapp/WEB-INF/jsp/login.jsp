<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login</title>
    <link href="../css/style.css" rel="stylesheet">
</head>
<body>
<jsp:useBean id="login" class="java.lang.String"/>

<%@include file="component/header.jsp" %>
<%@include file="component/menu.jsp" %>

<fmt:message bundle="${bundle}" key="login.summary" var="login_summary"/>
<fmt:message bundle="${bundle}" key="login.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="login.password" var="password_lable"/>
<fmt:message bundle="${bundle}" key="login.submit" var="try_login_button"/>

<div class="center-form">
    <form action="${pageContext.request.contextPath}/main?command=login"
          method="post">
        <%--        <input type="hidden" name="command" value="login"/>--%>
        <h3>${login_summary}</h3>
        <div class="total-message">
            ${message}
        </div>
        <div class="total-error">
            ${error}
        </div>
        <table>
            <tr>
                <td class="col-name">${login_lable}</td>
                <td>
                    <input type="text" name="login" value="${init.login[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>${errors.login}</td>
            </tr>
            <tr>
                <td class="col-name">${password_lable}</td>
                <td>
                    <input type="password" name="password"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>${errors.password}</td>
            </tr>
        </table>

        <input type="submit" value="${try_login_button}">
    </form>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
