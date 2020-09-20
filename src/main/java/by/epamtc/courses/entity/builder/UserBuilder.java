package by.epamtc.courses.entity.builder;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserRole;

import java.time.LocalDate;
import java.util.Map;

public class UserBuilder {

    public User createFromParams(final Map<String, String[]> parameterMap) {
        User user = new User();

        user.setLogin(parameterMap.get("login")[0]);
        user.setPassword(parameterMap.get("password")[0]);
        user.setSurname(parameterMap.get("surname")[0]);
        user.setName(parameterMap.get("name")[0]);
        user.setEmail(parameterMap.get("email")[0]);
        user.setBirthday(LocalDate.parse(parameterMap.get("birthday")[0]));
        user.setRole(UserRole.valueOf(parameterMap.get("role")[0]));

        return user;
    }
}
