package by.epamtc.courses.entity;

public enum UserRole {
    STUDENT(1),
    LECTURER(2);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
