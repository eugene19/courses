package by.epamtc.courses.entity.comparator;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that provides access to course comparator object
 *
 * @author DEA
 */
public final class CourseComparatorProvider {

    /**
     * Object of course comparator by start date
     */
    private static final Comparator<Course> START_DATE_COMPARATOR =
            Comparator.comparing(Course::getStartDate);

    /**
     * Object of course comparator by status
     */
    private static final Comparator<Course> STATUS_COMPARATOR =
            Comparator.comparingInt(course -> course.getStatus().getId());

    /**
     * Object of course comparator by summary
     */
    private static final Comparator<Course> SUMMARY_COMPARATOR =
            (course1, course2) -> course1.getSummary().compareToIgnoreCase(course2.getSummary());

    /**
     * Map that matches the name of a comparator with an object
     * that implements this comparator
     */
    private static Map<String, Comparator<Course>> comparators = new HashMap<>();

    static {
        comparators.put(ParameterName.START_DATE, START_DATE_COMPARATOR);
        comparators.put(ParameterName.STATUS, STATUS_COMPARATOR);
        comparators.put(ParameterName.SUMMARY, SUMMARY_COMPARATOR);
    }

    /**
     * Finds and returns a comparator by name. If such comparator
     * is absent return default comparator
     *
     * @param comparator name of comparator
     * @return object that implements this comparator
     */
    public static Comparator<Course> getComparator(String comparator) {
        return comparators.getOrDefault(comparator, SUMMARY_COMPARATOR);
    }
}
