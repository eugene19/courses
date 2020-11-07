package by.epamtc.courses.dao.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SqlUserDaoTest.class,
        SqlUserDaoStatusTest.class,
        SqlCourseDaoTest.class,
        SqlCourseDaoStatusTest.class
})
public class SqlDaoTestSuite {
}
