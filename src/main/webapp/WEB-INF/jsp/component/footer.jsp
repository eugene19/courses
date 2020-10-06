<fmt:message bundle="${bundle}" key="footer.policy" var="policy"/>
<fmt:message bundle="${bundle}" key="footer.address" var="address"/>
<fmt:message bundle="${bundle}" key="footer.mobile" var="mobile"/>

<footer class="my-1 pt-2 text-muted text-center text-small">
    <div class="right-block">
        ${policy}
    </div>
    <div>
        ${address}
    </div>
    <div class="right-block">
        <a class="text-info" href="#">${mobile}</a>
    </div>
</footer>
