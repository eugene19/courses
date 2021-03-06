package by.epamtc.courses.service.validation;

import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.entity.UserRole;
import by.epamtc.courses.service.i18n.LocaleMessage;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;

/**
 * Class for checking values of <code>User</code> attributes
 *
 * @author DEA
 */
public class UserValidator extends AbstractValidator {
    private static final Logger logger = Logger.getLogger(UserValidator.class);

    /**
     * Constant containing minimal date of user's birth
     */
    private static final String MINIMAL_BIRTH_DATE = "1900-01-01";

    /**
     * Pattern of valid user's login
     */
    private static final String LOGIN_PATTERN = "\\w{3,15}";

    /**
     * Pattern of valid user's password
     */
    private static final String PASSWORD_PATTERN = "\\w{3,15}";

    /**
     * Pattern of valid user's surname
     */
    private static final String SURNAME_PATTERN = "[A-Za-zА-Яа-яЁё]{2,15}";

    /**
     * Pattern of valid user's name
     */
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-яЁё]{2,15}";

    /**
     * Pattern of valid user's email
     */
    private static final String EMAIL_PATTERN = "\\w{3,15}@[A-Za-z]{3,15}\\.[A-Za-z]{1,4}";

    /**
     * Construct a UserValidator
     *
     * @param parameterMap request's parameters with values from client
     * @param locale       locale of client to select translation of error messages
     */
    public UserValidator(Map<String, String[]> parameterMap, Locale locale) {
        super(parameterMap, locale);
    }

    /**
     * Check if value of login is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateLogin() {
        String[] loginValues = parameterMap.get(ParameterName.LOGIN);

        if (checkEmpty(loginValues) || checkEmpty(loginValues[0])) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!loginValues[0].matches(LOGIN_PATTERN)) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_LOGIN));
        }

        return this;
    }

    /**
     * Check if value of password is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validatePassword() {
        String[] passwordValues = parameterMap.get(ParameterName.PASSWORD);

        if (checkEmpty(passwordValues) || checkEmpty(passwordValues[0])) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!passwordValues[0].matches(PASSWORD_PATTERN)) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_PASSWORD));
        }

        return this;
    }

    /**
     * Check if value of surname is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateSurname() {
        String[] surnameValues = parameterMap.get(ParameterName.SURNAME);

        if (checkEmpty(surnameValues) || checkEmpty(surnameValues[0])) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!surnameValues[0].matches(SURNAME_PATTERN)) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_SURNAME));
        }

        return this;
    }

    /**
     * Check if value of name is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateName() {
        String[] nameValues = parameterMap.get(ParameterName.NAME);

        if (checkEmpty(nameValues) || checkEmpty(nameValues[0])) {
            errors.put(ParameterName.NAME, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!nameValues[0].matches(NAME_PATTERN)) {
            errors.put(ParameterName.NAME, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_NAME));
        }

        return this;
    }

    /**
     * Check if value of email is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateEmail() {
        String[] emailValues = parameterMap.get(ParameterName.EMAIL);

        if (checkEmpty(emailValues) || checkEmpty(emailValues[0])) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!emailValues[0].matches(EMAIL_PATTERN)) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_EMAIL));
        }

        return this;
    }

    /**
     * Check if value of birthday is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateBirthday() {
        LocalDate minDate = LocalDate.parse(MINIMAL_BIRTH_DATE);
        LocalDate maxDate = LocalDate.now();

        String[] birthdayString = parameterMap.get(ParameterName.BIRTHDAY);
        LocalDate birthday;

        if (checkEmpty(birthdayString) || checkEmpty(birthdayString[0])) {
            errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                birthday = LocalDate.parse(birthdayString[0]);

                if (birthday.isBefore(minDate)) {
                    errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_DATE_BEFORE_MIN));
                } else if (birthday.isAfter(maxDate)) {
                    errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_DATE_AFTER_MAX));
                }
            } catch (DateTimeParseException e) {
                logger.error("Error while parsing date " + birthdayString[0]);
                errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_DATE));
            }
        }

        return this;
    }

    /**
     * Check if value of user's role is valid
     *
     * @return instance of validator object (this)
     */
    public UserValidator validateRole() {
        String[] roleValues = parameterMap.get(ParameterName.ROLE);

        if (checkEmpty(roleValues) || checkEmpty(roleValues[0])) {
            errors.put(ParameterName.ROLE, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                UserRole.valueOf(roleValues[0]);
            } catch (IllegalArgumentException e) {
                logger.error("Error while parsing user role: " + roleValues[0]);
                errors.put(ParameterName.ROLE, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_ROLE));
            }
        }

        return this;
    }
}
