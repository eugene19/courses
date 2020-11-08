package by.epamtc.courses.entity.builder;

import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class for creation Course entity
 *
 * @author DEA
 */
public class CourseBuilder {

    /**
     * Create <code>Course</code> entity from request's parameters
     *
     * @param parameters request's parameters from client
     * @return <code>Course</code> entity
     */
    public Course createCourseFromParams(final Map<String, String[]> parameters) {
        Course course = new Course();

        String[] idValues = parameters.get(ParameterName.ID);
        if (idValues != null) {
            course.setId(Integer.parseInt(idValues[0]));
        }

        String[] summaryValues = parameters.get(ParameterName.SUMMARY);
        if (summaryValues != null) {
            course.setSummary(summaryValues[0]);
        }

        String[] descriptionValues = parameters.get(ParameterName.DESCRIPTION);
        if (descriptionValues != null) {
            course.setDescription(descriptionValues[0]);
        }

        String[] startDateValues = parameters.get(ParameterName.START_DATE);
        if (startDateValues != null) {
            course.setStartDate(LocalDate.parse(startDateValues[0]));
        }

        String[] endDateValues = parameters.get(ParameterName.END_DATE);
        if (endDateValues != null) {
            course.setEndDate(LocalDate.parse(endDateValues[0]));
        }

        String[] studentsLimitValues = parameters.get(ParameterName.STUDENTS_LIMIT);
        if (studentsLimitValues != null) {
            course.setStudentsLimit(Integer.parseInt(studentsLimitValues[0]));
        }

        String[] lecturerIdValues = parameters.get(ParameterName.LECTURER_ID);
        if (lecturerIdValues != null) {
            course.setLecturerId(Integer.parseInt(lecturerIdValues[0]));
        }

        String[] statusValues = parameters.get(ParameterName.STATUS);
        if (statusValues != null) {
            course.setStatus(CourseStatus.valueOf(statusValues[0]));
        }

        return course;
    }
}
