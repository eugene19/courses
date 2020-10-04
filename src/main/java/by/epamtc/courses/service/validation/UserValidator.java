package by.epamtc.courses.service.validation;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.UserRole;
import by.epamtc.courses.service.i18n.ResourceManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserValidator {
    private static final String LOGIN_PATTERN = "\\w{3,15}";
    private static final String PASSWORD_PATTERN = "\\w{3,15}";
    private static final String SURNAME_PATTERN = "[A-Za-zА-Яа-яЁё]{3,15}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-яЁё]{3,15}";
    private static final String EMAIL_PATTERN = "\\w{3,15}@[A-Za-z]{3,15}\\.[A-Za-z]{1,4}";

    private Map<String, String[]> parameterMap;
    private Map<String, String> errors;
    private ResourceManager resourceManager;

    public UserValidator(Map<String, String[]> parameterMap, String lang) {
        this.parameterMap = parameterMap;
        this.errors = new HashMap<>();
        Locale locale = lang == null ? Locale.getDefault() : new Locale(lang);

        this.resourceManager = new ResourceManager(locale);
    }

    public UserValidator validateLogin() {
        String login = parameterMap.get(ParameterName.LOGIN)[0];

        if (checkEmpty(login)) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else if (!login.matches(LOGIN_PATTERN)) {
            errors.put(ParameterName.LOGIN, resourceManager.getValue("user.error.incorrectLogin"));
        }

        return this;
    }

    public UserValidator validatePassword() {
        String password = parameterMap.get(ParameterName.PASSWORD)[0];

        if (checkEmpty(password)) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else if (!password.matches(PASSWORD_PATTERN)) {
            errors.put(ParameterName.PASSWORD, resourceManager.getValue("user.error.incorrectPassword"));
        }

        return this;
    }

    public UserValidator validateSurname() {
        String surname = parameterMap.get(ParameterName.SURNAME)[0];

        if (checkEmpty(surname)) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else if (!surname.matches(SURNAME_PATTERN)) {
            errors.put(ParameterName.SURNAME, resourceManager.getValue("user.error.incorrectSurname"));
        }

        return this;
    }

    public UserValidator validateName() {
        String name = parameterMap.get(ParameterName.NAME)[0];

        if (checkEmpty(name)) {
            errors.put(ParameterName.NAME, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else if (!name.matches(NAME_PATTERN)) {
            errors.put(ParameterName.NAME, resourceManager.getValue("user.error.incorrectName"));
        }

        return this;
    }

    public UserValidator validateEmail() {
        String email = parameterMap.get(ParameterName.EMAIL)[0];

        if (checkEmpty(email)) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else if (!email.matches(EMAIL_PATTERN)) {
            errors.put(ParameterName.EMAIL, resourceManager.getValue("user.error.incorrectEmail"));
        }

        return this;
    }

    public UserValidator validateBirthday() {
        LocalDate minDate = LocalDate.parse("1900-01-01");
        LocalDate maxDate = LocalDate.now();

        String birthdayString = parameterMap.get(ParameterName.BIRTHDAY)[0];

        if (checkEmpty(birthdayString)) {
            errors.put(ParameterName.BIRTHDAY, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else {
            LocalDate birthday = LocalDate.parse(birthdayString);

            if (birthday.isBefore(minDate)) {
                errors.put(ParameterName.BIRTHDAY, resourceManager.getValue("user.error.dateBeforeMin"));
            } else if (birthday.isAfter(maxDate)) {
                errors.put(ParameterName.BIRTHDAY, resourceManager.getValue("user.error.dateAfterMax"));
            }
        }

        return this;
    }

    public UserValidator validateRole() {
        String role = parameterMap.get(ParameterName.ROLE)[0];

        if (checkEmpty(role)) {
            errors.put(ParameterName.ROLE, resourceManager.getValue("user.error.fieldIsEmpty"));
        } else {
            try {
                UserRole.valueOf(role);
            } catch (IllegalArgumentException e) {
                errors.put(ParameterName.ROLE, resourceManager.getValue("user.error.incorrectRole"));
            }
        }

        return this;
    }

    public UserValidator validateAll() {
        validateLogin();
        validatePassword();
        validateSurname();
        validateName();
        validateEmail();
        validateBirthday();
        validateRole();

        return this;
    }

    public boolean checkEmpty(String line) {
        return line == null || line.isEmpty();
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}