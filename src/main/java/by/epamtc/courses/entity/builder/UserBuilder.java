package by.epamtc.courses.entity.builder;

import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserRole;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class for creation User entity
 *
 * @author DEA
 */
public class UserBuilder {

    /**
     * Create <code>UserAuthData</code> entity from request's parameters
     *
     * @param parameters request's parameters from client
     * @return <code>UserAuthData</code> entity
     */
    public UserAuthData createUserDataFromParams(final Map<String, String[]> parameters) {
        User user = createUserFromParams(parameters);
        UserAuthData userAuthData = new UserAuthData(user);

        String[] loginValues = parameters.get(ParameterName.LOGIN);
        if (loginValues != null) {
            userAuthData.setLogin(loginValues[0]);
        }

        String[] passwordValues = parameters.get(ParameterName.PASSWORD);
        if (passwordValues != null) {
            userAuthData.setPassword(passwordValues[0]);
        }

        return userAuthData;
    }

    /**
     * Create <code>User</code> entity from request's parameters
     *
     * @param parameters request's parameters from client
     * @return <code>User</code> entity
     */
    public User createUserFromParams(final Map<String, String[]> parameters) {
        User user = new User();

        String[] idValues = parameters.get(ParameterName.ID);
        if (idValues != null) {
            user.setId(Integer.parseInt(idValues[0]));
        }

        String[] surnameValues = parameters.get(ParameterName.SURNAME);
        if (surnameValues != null) {
            user.setSurname(surnameValues[0]);
        }

        String[] nameValues = parameters.get(ParameterName.NAME);
        if (nameValues != null) {
            user.setName(nameValues[0]);
        }

        String[] emailValues = parameters.get(ParameterName.EMAIL);
        if (emailValues != null) {
            user.setEmail(emailValues[0]);
        }

        String[] birthdayValues = parameters.get(ParameterName.BIRTHDAY);
        if (birthdayValues != null) {
            user.setBirthday(LocalDate.parse(birthdayValues[0]));
        }

        String[] roleValues = parameters.get(ParameterName.ROLE);
        if (roleValues != null) {
            user.setRole(UserRole.valueOf(roleValues[0]));
        }

        String[] photoPathValues = parameters.get(ParameterName.PHOTO_FILE);
        if (photoPathValues != null) {
            user.setPhotoPath(photoPathValues[0]);
        }

        return user;
    }
}
