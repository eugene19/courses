<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="../../css/style.css" rel="stylesheet">
</head>
<body>
<%@include file="component/header.jsp" %>
<%@include file="component/menu.jsp" %>

<fmt:message bundle="${bundle}" key="profile.summary" var="profile_summary"/>
<fmt:message bundle="${bundle}" key="registration.login" var="login_lable"/>
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
<fmt:message bundle="${bundle}" key="profile.button.edit"
             var="edit_profile_button"/>

<div class="center-form">
    <div class="content-block"
         style="width: 60%; background: #D4E7EE; height: 100%">
        <h3>${profile_summary}</h3>
        <table>
            <tr>
                <td class="col-name">${surname_lable}</td>
                <td>
                    ${user.surname}
                </td>
            </tr>
            <tr>
                <td class="col-name">${name_lable}</td>
                <td>
                    ${user.name}
                </td>
            </tr>
            <tr>
                <td class="col-name">${email_lable}</td>
                <td>
                    ${user.email}
                </td>
            </tr>
            <tr>
                <td class="col-name">${birthday_lable}</td>
                <td>
                    ${user.birthday}
                </td>
            </tr>
            <tr>
                <td class="col-name">${role_lable}</td>
                <td>
                    <c:if test="${user.role == 'STUDENT'}">${role_student}</c:if>
                    <c:if test="${user.role == 'LECTURER'}">${role_lecturer}</c:if>
                </td>
            </tr>
        </table>

        <a href="${pageContext.request.contextPath}/main?command=get_edit_profile_page">${edit_profile_button}</a>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
