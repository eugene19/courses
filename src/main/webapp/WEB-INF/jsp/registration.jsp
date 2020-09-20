<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Registration</title>
    <style>
        <%@include file="../css/style.css"%>
    </style>
</head>
<body>
<%@include file="component/header.jsp" %>
<%@include file="component/menu.jsp" %>

<fmt:message bundle="${bundle}" key="registration.summary" var="registration_summary"/>
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
             var="student_select"/>
<fmt:message bundle="${bundle}" key="registration.role.lecturer"
             var="lecturer_select"/>

<div class="center-form">
    <form action="${pageContext.request.contextPath}/main?command=registration" method="post">
<%--        <input type="hidden" name="command" value="registration"/>--%>
        <h3>${registration_summary}</h3>
        <table>
            <tr>
                <td class="col-name">${login_lable}</td>
                <td>
                    <input id="login" type="text" name="login" value="${init.login[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.login}</p></td>
            </tr>
            <tr>
                <td class="col-name">${password_lable}</td>
                <td>
                    <input type="password" name="password"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.password}</p></td>
            </tr>
            <tr>
                <td class="col-name">${surname_lable}</td>
                <td>
                    <input type="text" name="surname" value="${init.surname[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.surname}</p></td>
            </tr>
            <tr>
                <td class="col-name">${name_lable}</td>
                <td>
                    <input type="text" name="name" value="${init.name[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.name}</p></td>
            </tr>
            <tr>
                <td class="col-name">${email_lable}</td>
                <td>
                    <input type="text" name="email" value="${init.email[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.email}</p></td>
            </tr>
            <tr>
                <td class="col-name">${birthday_lable}</td>
                <td>
                    <input type="date" name="birthday" value="${init.birthday[0]}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.birthday}</p></td>
            </tr>
            <tr>
                <td class="col-name">${role_lable}</td>
                <td>
                    <select name="role">
                        <option
                                <c:if test="${init.role[0] == 'STUDENT'}">selected</c:if>
                                value="STUDENT">${student_select}</option>
                        <option
                                <c:if test="${init.role[0] == 'LECTURER'}">selected</c:if>
                                value="LECTURER">${lecturer_select}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><p class="field-error">${errors.role}</p></td>
            </tr>
        </table>

        <input type="submit" value="${try_register_button}">
    </form>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
