package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SqlCourseDaoStatusTest {

    private final CourseDao courseDao = new SqlCourseDao();

    private final String courseStatus;
    private final int expectedCountOfCourses;

    public SqlCourseDaoStatusTest(String courseStatus, int expectedCountOfCourses) {
        this.courseStatus = courseStatus;
        this.expectedCountOfCourses = expectedCountOfCourses;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> studentStatusWithCountStudent() {
        return Arrays.asList(new Object[][]{
                {"'NOT_STARTED'", 4},
                {"'IN_PROGRESS'", 4},
                {"'FINISHED'", 3},
                {"'NOT_STARTED', 'IN_PROGRESS'", 8},
                {"'NOT_STARTED', 'FINISHED'", 7},
                {"'IN_PROGRESS', 'FINISHED'", 7},
                {"'NOT_STARTED', 'IN_PROGRESS', 'FINISHED'", 11},
        });
    }

    @Test(timeout = 100)
    public void countCoursesInStatus() throws DaoException {
        int actualCount = courseDao.countCoursesInStatus(courseStatus);

        assertEquals("Wrong count of courses with status " + courseStatus,
                expectedCountOfCourses, actualCount);
    }
}