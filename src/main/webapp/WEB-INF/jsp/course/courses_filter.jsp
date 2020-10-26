<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>

<fmt:message bundle="${bundle}" key="course.status.notStarted"
             var="not_started"/>
<fmt:message bundle="${bundle}" key="course.status.inProgress"
             var="in_progress"/>
<fmt:message bundle="${bundle}" key="course.status.finished" var="finished"/>
<fmt:message bundle="${bundle}" key="button.filter" var="filter_btn"/>
<fmt:message bundle="${bundle}" key="button.sort" var="sort_btn"/>
<fmt:message bundle="${bundle}" key="course.startDate" var="start_date_sort"/>
<fmt:message bundle="${bundle}" key="course.summary" var="summary_sort"/>
<fmt:message bundle="${bundle}" key="course.status" var="status_sort"/>

<div class="row">
    <form class="form-row pl-4" action="${pageContext.request.contextPath}/main"
          method="get">
        <input type="hidden" name="command" value="get_courses_page"/>

        <%--        FILERING--%>

        <div class="form-check form-check-inline">
            <div class="badge badge-primary text-wrap py-2 px-3"
                 style="border-radius: 15px 15px;">
                <span class="h6"><i
                        class="fa fa-filter text-light"></i> ${filter_btn}</span>
            </div>
        </div>

        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox"
                   id="not_started" name="status" value="NOT_STARTED"
                   onchange="submit()"
                    <myTag:checkIfContainValue array="${paramValues['status']}"
                                               findValue="NOT_STARTED"/>/>
            <label class="form-check-label"
                   for="not_started">${not_started}</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox"
                   id="in_progress" name="status" value="IN_PROGRESS"
                   onchange="submit()"
                    <myTag:checkIfContainValue array="${paramValues['status']}"
                                               findValue="IN_PROGRESS"/>/>
            <label class="form-check-label"
                   for="in_progress">${in_progress}</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox"
                   id="finished" name="status" value="FINISHED"
                   onchange="submit()"
                    <myTag:checkIfContainValue array="${paramValues['status']}"
                                               findValue="FINISHED"/>/>
            <label class="form-check-label" for="finished">${finished}</label>
        </div>

        <%--        SORTING--%>

        <div class="form-check form-check-inline ml-3">
            <div class="badge badge-primary text-wrap py-2 px-3"
                 style="border-radius: 15px 15px;">
                <span class="h6"><i
                        class="fa fa-sort-alpha-asc text-light"></i> ${sort_btn}</span>
            </div>
        </div>

        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="sort"
                   id="summary" value="summary" onchange="submit()" checked
            <myTag:checkIfContainValue array="${paramValues['sort']}"
                                       findValue="summary"/>>
            <label class="form-check-label"
                   for="summary">${summary_sort}</label>
        </div>

        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio"
                   name="sort" id="start_date" value="startDate"
                   onchange="submit()"
            <myTag:checkIfContainValue array="${paramValues['sort']}"
                                       findValue="startDate"/>>
            <label class="form-check-label"
                   for="start_date">${start_date_sort}</label>
        </div>

        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio"
                   name="sort" id="status" value="status" onchange="submit()"
            <myTag:checkIfContainValue array="${paramValues['sort']}"
                                       findValue="status"/>>
            <label class="form-check-label"
                   for="status">${status_sort}</label>
        </div>
    </form>
</div>