<fmt:message bundle="${bundle}" key="footer.policy" var="policy"/>
<fmt:message bundle="${bundle}" key="footer.address" var="address"/>
<fmt:message bundle="${bundle}" key="footer.mobile" var="mobile"/>

<div class="footer">
    <div class="right-block">
        ${policy}
    </div>
    <div>
        ${address}
    </div>
    <div class="right-block">
        <a href="#">${mobile}</a>
    </div>
</div>
