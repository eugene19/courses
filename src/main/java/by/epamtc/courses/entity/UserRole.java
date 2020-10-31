package by.epamtc.courses.entity;

/**
 * Enum containing all user's roles
 *
 * @author DEA
 */
public enum UserRole {

    /**
     * Role of student
     */
    STUDENT(1),

    /**
     * Role of lecturer
     */
    LECTURER(2);

    /**
     * Unique identifier of role
     */
    private final int id;

    /**
     * Construct user's role with id
     */
    UserRole(int id) {
        this.id = id;
    }

    /**
     * @return unique identifier of role
     */
    public int getId() {
        return id;
    }
}
