<jsp:useBean id="error" scope="request" type="java.lang.String"/>

<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${error}
    </div>
</c:if>