package by.epamtc.courses.entity.builder;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserRole;

import java.time.LocalDate;
import java.util.Map;

public class UserBuilder {

    public User createUserFromParams(final Map<String, String[]> parameters) {
        User user = new User();

        user.setSurname(parameters.get(ParameterName.SURNAME)[0]);
        user.setName(parameters.get(ParameterName.NAME)[0]);
        user.setEmail(parameters.get(ParameterName.EMAIL)[0]);
        user.setBirthday(LocalDate.parse(parameters.get(ParameterName.BIRTHDAY)[0]));
        user.setRole(UserRole.valueOf(parameters.get(ParameterName.ROLE)[0]));

        return user;
    }

    public UserAuthData createUserDataFromParams(final Map<String, String[]> parameters) {
        UserAuthData user = new UserAuthData();

        user.setLogin(parameters.get(ParameterName.LOGIN)[0]);
        user.setPassword(parameters.get(ParameterName.PASSWORD)[0]);
        user.setSurname(parameters.get(ParameterName.SURNAME)[0]);
        user.setName(parameters.get(ParameterName.NAME)[0]);
        user.setEmail(parameters.get(ParameterName.EMAIL)[0]);
        user.setBirthday(LocalDate.parse(parameters.get(ParameterName.BIRTHDAY)[0]));
        user.setRole(UserRole.valueOf(parameters.get(ParameterName.ROLE)[0]));

        return user;
    }
}
