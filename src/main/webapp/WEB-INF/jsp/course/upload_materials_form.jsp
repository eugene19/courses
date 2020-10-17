<div class="col-5 pl-0">
    <fmt:message bundle="${bundle}" key="courses.add.label.upload"
                 var="upload_label"/>
    <fmt:message bundle="${bundle}" key="button.upload"
                 var="upload_button"/>
    <fmt:message bundle="${bundle}" key="button.showUploading"
                 var="show_upload"/>

    <button class="spoiler-title btn btn-outline-primary"
            style="max-height: 40px">${show_upload}
    </button>

    <div class="spoiler-body ml-3" style="display:none;">
        <form action="${pageContext.request.contextPath}/main"
              method="post" enctype="multipart/form-data">
            <input type="hidden" name="command"
                   value="upload_course_materials"/>
            <input type="hidden" name="courseId"
                   value="${course.id}"/>

            <div class="form-group row">
                <label for="inputGroupFile02"
                       class="col-form-label text-muted">${upload_label}</label>
                <div class="custom-file">
                    <input type="file" class="custom-file-input"
                           id="inputGroupFile02" name="materials"
                           data-browse=""/>
                    <label class="custom-file-label"
                           for="inputGroupFile02"></label>
                </div>
            </div>
            <script>
                $("input[type=file]").change(function () {
                    var fieldVal = $(this).val();
                    fieldVal = fieldVal.replace("C:\\fakepath\\", "");

                    if (fieldVal !== undefined || fieldVal !== "") {
                        $(this).next(".custom-file-label").attr('data-content', fieldVal);
                        $(this).next(".custom-file-label").text(fieldVal);
                    }
                });
            </script>
            <div class="row justify-content-center py-1">
                <input class="btn btn-outline-info" type="submit"
                       value="${upload_button}"/>
            </div>
        </form>
    </div>
</div>