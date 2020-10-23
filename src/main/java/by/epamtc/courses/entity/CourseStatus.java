package by.epamtc.courses.entity;

/**
 * Enum containing all course's statuses
 *
 * @author DEA
 */
public enum CourseStatus {

    /**
     * Course not started status
     */
    NOT_STARTED(1),

    /**
     * Course in progress status
     */
    IN_PROGRESS(2),

    /**
     * Course finished status
     */
    FINISHED(3);

    /**
     * Unique identifier of status
     */
    private int id;

    /**
     * Construct CourseStatus with id
     */
    CourseStatus(int id) {
        this.id = id;
    }

    /**
     * @return unique identifier of status
     */
    public int getId() {
        return id;
    }
}
