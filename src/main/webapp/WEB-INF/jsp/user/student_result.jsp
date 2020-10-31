<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Student results</title>
    <%@include file="../component/link_style.jsp" %>
</head>
<body>
<%@include file="../component/header.jsp" %>
<fmt:message bundle="${bundle}" key="menu.account.marks"
             var="student_result_summary"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${student_result_summary}</h3>
    </div>

    <div class="row justify-content-center pt-2 pb-5">
        <%@include file="student_results_table.jsp" %>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
