package by.epamtc.courses.entity;

public enum CourseStatus {
    NOT_STARTED(1, "Не начат"),
    IN_PROGRESS(2, "В процессе"),
    FINISHED(3, "Закончен");

    private int id;
    private String status;

    CourseStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
