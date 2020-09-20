<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="strings" var="bundle"/>

<fmt:message bundle="${bundle}" key="header.ruButton" var="ru_button"/>
<fmt:message bundle="${bundle}" key="header.enButton" var="en_button"/>
<fmt:message bundle="${bundle}" key="header.login" var="login_button"/>
<fmt:message bundle="${bundle}" key="header.registration"
             var="register_button"/>
<fmt:message bundle="${bundle}" key="header.logout" var="logout_button"/>

<div class="header">
    <div class="block">
        <a href="${pageContext.request.contextPath}/">
            <img src="../../picture/logo.png" alt="Courses academy">
        </a>
    </div>
    <%--    <jsp:useBean id="user" class="by.epamtc.courses.entity.User"/>--%>
    <div class="block">
        <div class="block">
            <div class="lang">
                <form action="${pageContext.request.contextPath}/main?command=locale"
                      method="post">
<%--                    <input type="hidden" name="basePage" value="${pageContext.request.contextPath}">--%>
                    <select onchange="this.form.submit()" name="locale">
                        <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>
                            ${en_button}</option>
                        <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>
                            ${ru_button}</option>
                    </select>
                </form>
            </div>
        </div>
        <div class="block">
            <c:if test="${not empty user}">
                <div class="right-block">
                        ${user.name} ${user.surname}
                    <form action="${pageContext.request.contextPath}/main"
                          method="post">
                        <input type="hidden" name="command" value="logout">
                        <input type="submit" value="${logout_button}">
                    </form>
                </div>
            </c:if>

            <c:if test="${empty user}">
                <div class="right-block">
                    <a href="${pageContext.request.contextPath}/main?command=login">${login_button}</a><br/>
                    <a href="${pageContext.request.contextPath}/main?command=registration">${register_button}</a>
                </div>
            </c:if>
        </div>
    </div>
</div>
