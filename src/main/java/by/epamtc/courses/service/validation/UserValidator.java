package by.epamtc.courses.service.validation;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.UserRole;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserValidator {
    private static final String MINIMAL_DATE = "1900-01-01";

    private static final String LOGIN_PATTERN = "\\w{3,15}";
    private static final String PASSWORD_PATTERN = "\\w{3,15}";
    private static final String SURNAME_PATTERN = "[A-Za-zА-Яа-яЁё]{3,15}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-яЁё]{3,15}";
    private static final String EMAIL_PATTERN = "\\w{3,15}@[A-Za-z]{3,15}\\.[A-Za-z]{1,4}";

    private Map<String, String[]> parameterMap;
    private Map<String, String> errors;
    private ResourceManager resourceManager;

    public UserValidator(Map<String, String[]> parameterMap, Locale locale) {
        this.parameterMap = parameterMap;
        this.errors = new HashMap<>();
        this.resourceManager = new ResourceManager(locale);
    }

    public UserValidator validateLogin() {
        String login = parameterMap.get(ParameterName.LOGIN)[0];

        if (checkEmpty(login)) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!login.matches(LOGIN_PATTERN)) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_LOGIN));
        }

        return this;
    }

    public UserValidator validatePassword() {
        String password = parameterMap.get(ParameterName.PASSWORD)[0];

        if (checkEmpty(password)) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!password.matches(PASSWORD_PATTERN)) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_PASSWORD));
        }

        return this;
    }

    public UserValidator validateSurname() {
        String surname = parameterMap.get(ParameterName.SURNAME)[0];

        if (checkEmpty(surname)) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!surname.matches(SURNAME_PATTERN)) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_SURNAME));
        }

        return this;
    }

    public UserValidator validateName() {
        String name = parameterMap.get(ParameterName.NAME)[0];

        if (checkEmpty(name)) {
            errors.put(ParameterName.NAME, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!name.matches(NAME_PATTERN)) {
            errors.put(ParameterName.NAME, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_NAME));
        }

        return this;
    }

    public UserValidator validateEmail() {
        String email = parameterMap.get(ParameterName.EMAIL)[0];

        if (checkEmpty(email)) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!email.matches(EMAIL_PATTERN)) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_EMAIL));
        }

        return this;
    }

    public UserValidator validateBirthday() {
        LocalDate minDate = LocalDate.parse(MINIMAL_DATE);
        LocalDate maxDate = LocalDate.now();

        String birthdayString = parameterMap.get(ParameterName.BIRTHDAY)[0];

        if (checkEmpty(birthdayString)) {
            errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            LocalDate birthday = LocalDate.parse(birthdayString);

            if (birthday.isBefore(minDate)) {
                errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_DATE_BEFORE_MIN));
            } else if (birthday.isAfter(maxDate)) {
                errors.put(ParameterName.BIRTHDAY, resourceManager.getValue(LocaleMessage.ERROR_DATE_AFTER_MAX));
            }
        }

        return this;
    }

    public UserValidator validateRole() {
        String role = parameterMap.get(ParameterName.ROLE)[0];

        if (checkEmpty(role)) {
            errors.put(ParameterName.ROLE, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                UserRole.valueOf(role);
            } catch (IllegalArgumentException e) {
                errors.put(ParameterName.ROLE, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_ROLE));
            }
        }

        return this;
    }

    public boolean checkEmpty(String line) {
        return line == null || line.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
