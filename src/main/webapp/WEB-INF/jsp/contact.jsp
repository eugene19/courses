<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Contacts</title>
    <%@include file="component/link_style.jsp" %>
</head>
<body>
<%@include file="component/header.jsp" %>

<fmt:message bundle="${bundle}" key="contacts.summary" var="contacts_summary"/>
<fmt:message bundle="${bundle}" key="contacts.address.summary"
             var="address_summary"/>
<fmt:message bundle="${bundle}" key="footer.address" var="address"/>
<fmt:message bundle="${bundle}" key="contacts.phones.summary"
             var="phones_summary"/>
<fmt:message bundle="${bundle}" key="contacts.phones" var="phones"/>
<fmt:message bundle="${bundle}" key="contacts.workSchedule.summary"
             var="workSchedule_summary"/>
<fmt:message bundle="${bundle}" key="contacts.workSchedule" var="workSchedule"/>

<div class="container">
    <div class="row justify-content-center my-5">
        <h3 class="h3 mb-3 font-weight-normal">${contacts_summary}</h3>
    </div>

    <div class="row py-5">
        <h3 class="h3 mb-3 font-weight-normal">${address_summary}</h3>
        <div class="row container-fluid">
            ${address}
        </div>
        <div class="row container-fluid">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2351.0325171437325!2d27.54985211579111!3d53.895625980097805!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcfc2ad550759%3A0x57deacf90acc4fff!2z0YPQuy4g0JrQsNGA0LvQsCDQnNCw0YDQutGB0LAgMSwg0JzQuNC90YHQug!5e0!3m2!1sru!2sby!4v1602341247125!5m2!1sru!2sby"
                    width="600" height="450" style="border:0;"
                    allowfullscreen="" aria-hidden="false"
                    tabindex="0"></iframe>
        </div>
    </div>

    <div class="row py-5">
        <h3 class="h3 mb-3 font-weight-normal">${phones_summary}</h3>
        <div class="row container-fluid">
            ${phones}
        </div>
    </div>

    <div class="row py-5">
        <h3 class="h3 mb-3 font-weight-normal">${workSchedule_summary}</h3>
        <div class="row container-fluid">
            ${workSchedule}
        </div>
    </div>
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
