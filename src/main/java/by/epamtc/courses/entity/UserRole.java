package by.epamtc.courses.entity;

public enum UserRole {
    STUDENT(1, "Student"),
    LECTURER(2, "Lecture");

    private int id;
    private String text;

    UserRole(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public static UserRole getById(int id) {
        for (UserRole value : UserRole.values()) {
            if (value.id == id) {
                return value;
            }
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
