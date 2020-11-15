<%--@elvariable id="user" type="by.epamtc.courses.entity.User"--%>
<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="init" type="java.util.Map"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <%@include file="../component/link_style.jsp" %>
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
            integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
            crossorigin="anonymous"></script>
</head>
<body>
<%@include file="../component/header.jsp" %>

<fmt:message bundle="${bundle}" key="profile.edit.summary"
             var="edit_profile_summary"/>
<fmt:message bundle="${bundle}" key="user.login" var="login_lable"/>
<fmt:message bundle="${bundle}" key="user.surname" var="surname_lable"/>
<fmt:message bundle="${bundle}" key="user.name" var="name_lable"/>
<fmt:message bundle="${bundle}" key="user.email" var="email_lable"/>
<fmt:message bundle="${bundle}" key="user.birthday"
             var="birthday_lable"/>
<fmt:message bundle="${bundle}" key="user.role" var="role_lable"/>
<fmt:message bundle="${bundle}" key="user.role.student"
             var="role_student"/>
<fmt:message bundle="${bundle}" key="user.role.lecturer"
             var="role_lecturer"/>
<fmt:message bundle="${bundle}" key="button.edit"
             var="save_profile_button"/>
<fmt:message bundle="${bundle}" key="error.fieldImmutable"
             var="immutable_field"/>
<fmt:message bundle="${bundle}" key="profile.edit.label.upload"
             var="upload_label"/>
<fmt:message bundle="${bundle}" key="button.upload"
             var="upload_button"/>
<fmt:message bundle="${bundle}" key="error.veryBigFile"
             var="fileVeryBig"/>

<div class="container">
    <div class="row justify-content-center py-5">
        <h3 class="h3 mb-3 font-weight-normal">${edit_profile_summary}</h3>
    </div>

    <div class="row justify-content-center py-2">
        <form class="col-md-7" method="post"
              action="${pageContext.request.contextPath}/main"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="upload_user_photo"/>

            <c:choose>
                <c:when test="${not empty user.photoPath}">
                    <div class="form-group row">
                        <img class="rounded mx-auto d-block w-50"
                             src="/uploadFiles/${user.id}/${user.photoPath}"
                             alt="User photo">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group row">
                        <img class="rounded mx-auto d-block w-50"
                             src="${pageContext.request.contextPath}/uploadFiles/default/defaultPhoto.png"
                             alt="User default photo">
                    </div>
                </c:otherwise>
            </c:choose>

            <%@include file="../component/alert_error.jsp" %>

            <div class="form-group row justify-content-center">
                <label for="inputGroupFile02"
                       class="col-lg-3 col-form-label text-muted text-right">${upload_label}</label>
                <div class="col-lg-6">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input"
                               id="inputGroupFile02" name="photoFile"
                               data-browse="" required
                               accept=".png,.jpg,.jpeg"/>
                        <label class="custom-file-label"
                               for="inputGroupFile02"></label>
                    </div>
                    <div class="input-group-append my-2">
                        <button class="btn btn-outline-info">${upload_button}</button>
                    </div>
                </div>
            </div>
            <script>
                $("input[type=file]").change(function () {
                    var fieldVal = $(this).val();
                    fieldVal = fieldVal.replace("C:\\fakepath\\", "");

                    if (fieldVal !== undefined || fieldVal !== "") {
                        var file = document.getElementById("inputGroupFile02").files[0];

                        if (file.size > 500_000) {
                            document.getElementById("inputGroupFile02").value = null;
                            alert("${fileVeryBig}");
                        } else {
                            if (file.name.length > 15) {
                                $(this).next(".custom-file-label").attr('data-content', fieldVal.substring(0, 15) + '...');
                                $(this).next(".custom-file-label").text(fieldVal.substring(0, 15) + '...');
                            } else {
                                $(this).next(".custom-file-label").attr('data-content', fieldVal);
                                $(this).next(".custom-file-label").text(fieldVal);
                            }
                        }
                    }
                });
            </script>
        </form>

        <form class="col-md-7" action="${pageContext.request.contextPath}/main"
              method="post">
            <input type="hidden" name="command" value="edit_profile"/>
            <input type="hidden" name="id" value="${user.id}"/>
            <input type="hidden" name="photoFile" value="${user.photoPath}"/>

            <div class="form-group row justify-content-center">
                <label for="surname"
                       class="col-lg-3 col-form-label text-muted text-right">${surname_lable}
                    <span class="text-danger">*</span></label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="surname" name="surname"
                           value="<c:if test="${init.surname[0] != null}">${init.surname[0]}</c:if><c:if test="${init.surname[0] == null}">${user.surname}</c:if>"
                           required pattern="[A-Za-zА-Яа-яЁё]{2,15}"
                           minlength="2" maxlength="15"/>
                    <div class="text-danger small col-lg-11">${errors.surname}</div>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="name"
                       class="col-lg-3 col-form-label text-muted text-right">${name_lable}
                    <span class="text-danger">*</span></label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="name" name="name"
                           value="<c:if test="${init.name[0] != null}">${init.name[0]}</c:if><c:if test="${init.name[0] == null}">${user.name}</c:if>"
                           required pattern="[A-Za-zА-Яа-яЁё]{2,15}"
                           minlength="2" maxlength="15"/>
                    <div class="text-danger small col-lg-11">${errors.name}</div>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="email"
                       class="col-lg-3 col-form-label text-muted text-right">${email_lable}
                    <span class="text-danger">*</span></label>
                <div class="col-lg-6">
                    <input type="text" class="form-control"
                           id="email" name="email" required
                           value="<c:if test="${init.email[0] != null}">${init.email[0]}</c:if><c:if test="${init.email[0] == null}">${user.email}</c:if>"
                           pattern="\w{3,15}@[A-Za-z]{3,15}\.[A-Za-z]{1,4}"
                           minlength="9" maxlength="45"/>
                    <div class="text-danger small col-lg-11">${errors.email}</div>
                </div>
            </div>

            <jsp:useBean id="currentDate" class="java.util.Date"/>

            <div class="form-group row justify-content-center">
                <label for="birthday"
                       class="col-lg-3 col-form-label text-muted text-right">${birthday_lable}
                    <span class="text-danger">*</span></label>
                <div class="col-lg-6">
                    <input type="date" class="form-control"
                           id="birthday" name="birthday" required
                           min="1900-01-01"
                           value="<c:if test="${init.birthday[0] != null}">${init.birthday[0]}</c:if><c:if test="${init.birthday[0] == null}">${user.birthday}</c:if>"
                           max="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>"/>
                    <div class="text-danger small col-lg-11">${errors.birthday}</div>
                </div>
            </div>

            <div class="form-group row justify-content-center">
                <label for="role"
                       class="col-lg-3 col-form-label text-muted text-right">${role_lable}</label>
                <input type="hidden" name="role" required
                       value="<c:if test="${init.role[0] != null}">${init.role[0]}</c:if><c:if test="${init.role[0] == null}">${user.role}</c:if>"/>
                <div class="col-lg-6">
                    <select disabled class="form-control disabled" id="role">
                        <c:choose>
                            <c:when test="${user.role  == 'STUDENT'}">
                                <option selected>${role_student}</option>
                            </c:when>
                            <c:when test="${user.role  == 'LECTURER'}">
                                <option selected>${role_lecturer}</option>
                            </c:when>
                        </c:choose>
                    </select>
                    <div class="text-warning small col-lg-11">${immutable_field}</div>
                </div>
            </div>

            <div class="row align-items-center justify-content-center py-2">
                <button class="btn btn-outline-info"
                        type="submit">${save_profile_button}</button>
            </div>
        </form>
        <%@include file="../component/field_validation.jsp" %>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
</html>
