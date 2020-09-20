<fmt:message bundle="${bundle}" key="menu.main" var="main_button"/>
<fmt:message bundle="${bundle}" key="menu.about" var="about_button"/>
<fmt:message bundle="${bundle}" key="menu.contacts" var="contacts_button"/>

<nav class="menu">
    <ul>
        <li><a href="${pageContext.request.contextPath}/">${main_button}</a></li>
        <li><a href="${pageContext.request.contextPath}/main?command=about">${about_button}</a></li>
        <li><a href="${pageContext.request.contextPath}/main?command=contacts">${contacts_button}</a></li>
    </ul>
</nav>
