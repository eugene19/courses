package by.epamtc.courses.entity;

public enum UserCourseStatus {
    APPLIED(1),
    ENTERED(2),
    NOT_ENTERED(3);

    private int id;

    UserCourseStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
