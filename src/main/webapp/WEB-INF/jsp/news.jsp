<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>News</title>
    <link href="../../css/style.css" rel="stylesheet">
</head>
<body>
<%@include file="component/header.jsp" %>
<%@include file="component/menu.jsp" %>

<div class="content">
    ${news}
</div>

<%@include file="component/footer.jsp" %>
</body>
</html>
