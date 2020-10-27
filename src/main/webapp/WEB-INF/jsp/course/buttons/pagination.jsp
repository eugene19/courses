<fmt:message bundle="${bundle}" key="button.previous" var="previous_button"/>
<fmt:message bundle="${bundle}" key="button.next" var="next_button"/>

<c:set value="${param.get('page')}" var="currentPage"/>

<c:if test="${coursesCount > 5}">
    <div class="row align-text-bottom justify-content-center">
        <c:if test="${currentPage > 0}">
            <a class="pr-3 text-info"
               href="${pageContext.request.contextPath}/main?command=get_courses_page&page=${currentPage - 1}">${previous_button}</a>
        </c:if>

        <c:set var="pageCounter" value="${0}"/>
        <c:forEach var="num" begin="0" end="${coursesCount - 1}" step="5">
            <c:choose>
                <c:when test="${currentPage == pageCounter || (currentPage == null && pageCounter == 0)}">
                    <a class="mx-1 h5 text-secondary"
                       href="${pageContext.request.contextPath}/main?command=get_courses_page&page=${pageCounter}">${pageCounter = pageCounter + 1}</a>
                </c:when>
                <c:otherwise>
                    <a class="mx-1 text-info"
                       href="${pageContext.request.contextPath}/main?command=get_courses_page&page=${pageCounter}">${pageCounter = pageCounter + 1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage != pageCounter - 1}">
            <a class="pl-3 text-info"
               href="${pageContext.request.contextPath}/main?command=get_courses_page&page=${currentPage + 1}">${next_button}</a>
        </c:if>
    </div>
</c:if>