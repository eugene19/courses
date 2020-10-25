<%@ taglib prefix="myTag" uri="/WEB-INF/tld/myTag" %>

<fmt:message bundle="${bundle}" key="course.status.notStarted"
             var="not_started"/>
<fmt:message bundle="${bundle}" key="course.status.inProgress"
             var="in_progress"/>
<fmt:message bundle="${bundle}" key="course.status.finished" var="finished"/>
<fmt:message bundle="${bundle}" key="button.filter" var="filter_btn"/>

<div class="row">
    <form class="form-row pl-4" action="${pageContext.request.contextPath}/main"
          method="get">
        <input type="hidden" name="command" value="get_courses_page"/>

        <div class="form-check form-check-inline">
            <div class="badge badge-primary text-wrap py-2"
                 style="width: 6rem; border-radius: 15px 15px;">
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
    </form>
</div>