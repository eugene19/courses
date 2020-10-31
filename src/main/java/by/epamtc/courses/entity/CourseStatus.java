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
    private final int id;

    /**
     * Construct CourseStatus with id
     */
    CourseStatus(int id) {
        this.id = id;
    }

    /**
     * @return array with names of all statuses
     */
    public static String[] getStatusesNames() {
        CourseStatus[] statuses = CourseStatus.values();
        String[] statusNames = new String[statuses.length];

        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].toString();
        }
        return statusNames;
    }

    /**
     * @return unique identifier of status
     */
    public int getId() {
        return id;
    }
}
