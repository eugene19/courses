<fmt:message bundle="${bundle}" key="error.fieldIsEmpty" var="field_empty"/>
<fmt:message bundle="${bundle}" key="error.wrongValue" var="field_invalid"/>
<fmt:message bundle="${bundle}" key="error.valueUnder" var="field_underflow"/>
<fmt:message bundle="${bundle}" key="error.valueOver" var="field_overflow"/>
<fmt:message bundle="${bundle}" key="error.valueShort" var="field_short"/>
<fmt:message bundle="${bundle}" key="error.valueLong" var="field_long"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript">
    $('input').on('input invalid', function () {
        this.setCustomValidity('')
        if (this.validity.valueMissing) {
            this.setCustomValidity("${field_empty}")
        }
        if (this.validity.patternMismatch) {
            this.setCustomValidity("${field_invalid}")
        }
        if (this.validity.rangeUnderflow) {
            this.setCustomValidity("${field_underflow} " + this.min)
        }
        if (this.validity.rangeOverflow) {
            this.setCustomValidity("${field_overflow} " + this.max)
        }
        if (this.validity.tooShort) {
            this.setCustomValidity("${field_short}: " + this.minLength)
        }
        if (this.validity.tooLong) {
            this.setCustomValidity("${field_long}: " + this.maxLength)
        }
    })
</script>
<script type="text/javascript">
    $('textarea').on('textarea invalid', function () {
        this.setCustomValidity('')
        if (this.validity.valueMissing) {
            this.setCustomValidity("${field_empty}")
        }
        if (this.validity.patternMismatch) {
            this.setCustomValidity("${field_invalid}")
        }
        if (this.validity.rangeUnderflow) {
            this.setCustomValidity("${field_underflow} " + this.min)
        }
        if (this.validity.rangeOverflow) {
            this.setCustomValidity("${field_overflow} " + this.max)
        }
        if (this.validity.tooShort) {
            this.setCustomValidity("${field_short}: " + this.minLength)
        }
        if (this.validity.tooLong) {
            this.setCustomValidity("${field_long}: " + this.maxLength)
        }
    })
</script>