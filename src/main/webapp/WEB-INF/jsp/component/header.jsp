<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="strings" var="bundle"/>

<fmt:message bundle="${bundle}" key="header.ruButton" var="ru_button"/>
<fmt:message bundle="${bundle}" key="header.enButton" var="en_button"/>
<fmt:message bundle="${bundle}" key="header.login" var="login_button"/>
<fmt:message bundle="${bundle}" key="header.registration"
             var="register_button"/>
<fmt:message bundle="${bundle}" key="header.logout" var="logout_button"/>
<fmt:message bundle="${bundle}" key="menu.main" var="main_button"/>
<fmt:message bundle="${bundle}" key="menu.courses" var="courses_button"/>
<fmt:message bundle="${bundle}" key="menu.about" var="about_button"/>
<fmt:message bundle="${bundle}" key="menu.contacts" var="contacts_button"/>
<fmt:message bundle="${bundle}" key="menu.profile" var="profile_button"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="../../picture/logo.png" alt="Courses academy">
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
                       href="${pageContext.request.contextPath}/main?command=contacts">${contacts_button}</a>
                </li>
                <c:if test="${not empty user}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/main?command=get_profile_page">${profile_button}</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <form class="form-inline my-1 mx-2"
              action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="locale"/>

            <select class="form-control form-control-sm" onchange="submit()"
                    name="locale">
                <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>
                    ${en_button}</option>
                <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>
                    ${ru_button}</option>
            </select>
        </form>
        <c:if test="${not empty user}">
            <form class="my-1 d-flex align-items-center"
                  action="${pageContext.request.contextPath}/main"
                  method="post">
                <input type="hidden" name="command" value="logout"/>

                <span class="navbar-text col-lg-7">
                    ${user.name} ${user.surname}
                </span>
                <button class="btn btn-sm btn-outline-secondary col-lg-5 ml-2"
                        type="submit">${logout_button}</button>
            </form>
        </c:if>

        <c:if test="${empty user}">
            <form class="my-1">
                <a class="text-info"
                   href="${pageContext.request.contextPath}/main?command=get_login_page">${login_button}</a><br/>
                <a class="text-info"
                   href="${pageContext.request.contextPath}/main?command=get_registration_page">${register_button}</a>
            </form>
        </c:if>
    </div>
</nav>
