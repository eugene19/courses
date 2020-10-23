package by.epamtc.courses.entity;

/**
 * Enum containing all student's statuses
 *
 * @author DEA
 */
public enum UserCourseStatus {

    /**
     * Student applied on course
     */
    APPLIED(1),

    /**
     * Student entered on course
     */
    ENTERED(2),

    /**
     * Student not entered on course
     */
    NOT_ENTERED(3);

    /**
     * Unique identifier of student's status
     */
    private int id;

    /**
     * Construct UserCourseStatus with id
     */
    UserCourseStatus(int id) {
        this.id = id;
    }

    /**
     * @return unique identifier of student's status
     */
    public int getId() {
        return id;
    }
}
