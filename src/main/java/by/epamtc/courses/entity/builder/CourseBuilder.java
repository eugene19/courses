package by.epamtc.courses.entity.builder;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;

import java.time.LocalDate;
import java.util.Map;

public class CourseBuilder {

    public Course createCourseFromParams(final Map<String, String[]> parameters) {
        Course course = new Course();

        course.setSummary(parameters.get(ParameterName.SUMMARY)[0]);
        course.setDescription(parameters.get(ParameterName.DESCRIPTION)[0]);
        course.setStartDate(LocalDate.parse(parameters.get(ParameterName.START_DATE)[0]));
        course.setEndDate(LocalDate.parse(parameters.get(ParameterName.END_DATE)[0]));
        course.setStudentsLimit(Integer.parseInt(parameters.get(ParameterName.STUDENTS_LIMIT)[0]));

        return course;
    }
}
