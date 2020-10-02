package by.epamtc.courses.entity.builder;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserRole;

import java.time.LocalDate;
import java.util.Map;

public class UserBuilder {

    public User createUserFromParams(final Map<String, String[]> parameters) {
        User user = new User();

        user.setSurname(parameters.get("surname")[0]);
        user.setName(parameters.get("name")[0]);
        user.setEmail(parameters.get("email")[0]);
        user.setBirthday(LocalDate.parse(parameters.get("birthday")[0]));
        user.setRole(UserRole.valueOf(parameters.get("role")[0]));

        return user;
    }

    public UserAuthData createUserDataFromParams(final Map<String, String[]> parameters) {
        UserAuthData user = new UserAuthData();

        user.setLogin(parameters.get("login")[0]);
        user.setPassword(parameters.get("password")[0]);
        user.setSurname(parameters.get("surname")[0]);
        user.setName(parameters.get("name")[0]);
        user.setEmail(parameters.get("email")[0]);
        user.setBirthday(LocalDate.parse(parameters.get("birthday")[0]));
        user.setRole(UserRole.valueOf(parameters.get("role")[0]));

        return user;
    }
}
