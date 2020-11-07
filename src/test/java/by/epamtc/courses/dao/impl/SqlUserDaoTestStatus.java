package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserCourseStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SqlUserDaoTestStatus {

    private final UserDao userDao = new SqlUserDao();

    private final UserCourseStatus studentStatus;
    private final int expectedCountOfStudents;

    public SqlUserDaoTestStatus(UserCourseStatus studentStatus, int expectedCountOfStudents) {
        this.studentStatus = studentStatus;
        this.expectedCountOfStudents = expectedCountOfStudents;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> studentStatusWithCountStudent() {
        return Arrays.asList(new Object[][]{
                {UserCourseStatus.APPLIED, 6},
                {UserCourseStatus.ENTERED, 1},
                {UserCourseStatus.NOT_ENTERED, 1}
        });
    }

    @Test(timeout = 100)
    public void findStudentsOnCourseWithStatus() throws DaoException {
        int courseId = 4;
        Map<User, UserCourseStatus> students = userDao.findStudentsOnCourseWithStatus(courseId, studentStatus);

        assertEquals("Wrong count of students with status " + studentStatus,
                expectedCountOfStudents, students.size());
    }
}