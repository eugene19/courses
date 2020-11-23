<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="strings" var="bundle"/>

<fmt:message bundle="${bundle}" key="header.ruButton" var="ru_button"/>
<fmt:message bundle="${bundle}" key="header.enButton" var="en_button"/>
<fmt:message bundle="${bundle}" key="header.login" var="login_button"/>
<fmt:message bundle="${bundle}" key="header.registration"
             var="register_btn"/>
<fmt:message bundle="${bundle}" key="header.logout" var="logout_button"/>
<fmt:message bundle="${bundle}" key="menu.main" var="main_button"/>
<fmt:message bundle="${bundle}" key="menu.courses" var="courses_button"/>
<fmt:message bundle="${bundle}" key="menu.about" var="about_button"/>
<fmt:message bundle="${bundle}" key="menu.contacts" var="contacts_button"/>
<fmt:message bundle="${bundle}" key="menu.profile" var="profile_button"/>
<fmt:message bundle="${bundle}" key="menu.account" var="account_button"/>
<fmt:message bundle="${bundle}" key="menu.account.marks" var="marks_button"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="../../../picture/logo.png" alt="Courses academy">
            </a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/">${main_button}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/main?command=get_courses_page">${courses_button}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/main?command=get_about_us_page">${about_button}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/main?command=get_contact_page">${contacts_button}</a>
                </li>
                <c:if test="${not empty user}">
                    <script type="text/javascript"
                            src="https://itchief.ru/examples/vendors/jquery/jquery-3.4.1.min.js"></script>
                    <script type="text/javascript"
                            src="https://itchief.ru/examples/vendors/bootstrap-4.3/js/bootstrap.bundle.min.js"></script>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle"
                           data-toggle="dropdown" role="button"
                           aria-haspopup="true"
                           aria-expanded="false">${account_button}</a>
                        <div class="dropdown-menu">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/main?command=get_profile_page">${profile_button}</a>
                            <c:if test="${user.role == 'STUDENT'}">
                                <a class="nav-link"
                                   href="${pageContext.request.contextPath}/main?command=get_students_result_page">${marks_button}</a>
                            </c:if>
                        </div>
                    </li>
                </c:if>
            </ul>
        </div>
        <form class="form-inline my-1 mx-2"
              action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="locale"/>

            <label>
                <select class="form-control form-control-sm" onchange="submit()"
                        name="locale">
                    <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>
                        ${en_button}</option>
                    <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>
                        ${ru_button}</option>
                </select>
            </label>
        </form>

        <c:choose>
            <c:when test="${not empty user}">
                <form class="my-1 d-flex align-items-center"
                      action="${pageContext.request.contextPath}/main"
                      method="post">
                    <input type="hidden" name="command" value="logout"/>

                    <span class="navbar-text col-lg-7">
                    ${fn:escapeXml(user.name)} ${fn:escapeXml(user.surname)}
                </span>
                    <button class="btn btn-sm btn-outline-secondary col-lg-5 ml-2"
                            type="submit">${logout_button}</button>
                </form>
            </c:when>
            <c:otherwise>
                <form class="my-1">
                    <a class="text-info"
                       href="${pageContext.request.contextPath}/main?command=get_login_page">${login_button}</a><br/>
                    <a class="text-info"
                       href="${pageContext.request.contextPath}/main?command=get_registration_page">${register_btn}</a>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
