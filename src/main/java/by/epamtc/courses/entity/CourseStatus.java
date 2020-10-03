package by.epamtc.courses.entity;

public enum CourseStatus {
    NOT_STARTED(1),
    IN_PROGRESS(2),
    FINISHED(3);

    private int id;

    CourseStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
