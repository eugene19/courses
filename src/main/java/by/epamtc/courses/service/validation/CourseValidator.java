package by.epamtc.courses.service.validation;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CourseValidator {
    private static final Logger LOGGER = Logger.getLogger(CourseValidator.class);

    private static final String MAXIMAL_DATE = "2099-12-31";
    private static final int MAXIMAL_STUDENTS_LIMIT = 99;
    private static final int MINIMAL_STUDENTS_LIMIT = 1;

    private static final String SUMMARY_PATTERN = ".{3,100}";
    private static final String DESCRIPTION_PATTERN = ".{3,1130}";

    private Map<String, String[]> parameterMap;
    private Map<String, String> errors;
    private ResourceManager resourceManager;

    public CourseValidator(Map<String, String[]> parameterMap, Locale locale) {
        this.parameterMap = parameterMap;
        this.errors = new HashMap<>();
        this.resourceManager = new ResourceManager(locale);
    }

    public CourseValidator validateSummary() {
        String[] summaryValues = parameterMap.get(ParameterName.SUMMARY);

        if (checkEmpty(summaryValues) || checkEmpty(summaryValues[0])) {
            errors.put(ParameterName.SUMMARY, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!summaryValues[0].matches(SUMMARY_PATTERN)) {
            errors.put(ParameterName.SUMMARY, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_SUMMARY));
        }

        return this;
    }

    public CourseValidator validateDescription() {
        String[] descriptionValues = parameterMap.get(ParameterName.DESCRIPTION);

        if (checkEmpty(descriptionValues) || checkEmpty(descriptionValues[0])) {
            errors.put(ParameterName.DESCRIPTION, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!descriptionValues[0].matches(DESCRIPTION_PATTERN)) {
            errors.put(ParameterName.DESCRIPTION, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_DESCRIPTION));
        }

        return this;
    }

    public CourseValidator validateStartDate() {
        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = LocalDate.parse(MAXIMAL_DATE);

        String[] startDateString = parameterMap.get(ParameterName.START_DATE);
        LocalDate startDate;

        if (checkEmpty(startDateString) || checkEmpty(startDateString[0])) {
            errors.put(ParameterName.START_DATE, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                startDate = LocalDate.parse(startDateString[0]);

                if (startDate.isBefore(minDate)) {
                    errors.put(ParameterName.START_DATE, resourceManager.getValue(LocaleMessage.ERROR_COURSE_DATE_BEFORE_MIN));
                } else if (startDate.isAfter(maxDate)) {
                    errors.put(ParameterName.START_DATE, resourceManager.getValue(LocaleMessage.ERROR_COURSE_DATE_AFTER_MAX));
                }
            } catch (DateTimeParseException e) {
                LOGGER.error("Error while parsing date " + startDateString[0]);
                errors.put(ParameterName.START_DATE, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_DATE));
            }
        }

        return this;
    }

    public CourseValidator validateEndDate() {
        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = LocalDate.parse(MAXIMAL_DATE);

        String[] endDateString = parameterMap.get(ParameterName.END_DATE);
        LocalDate endDate;

        if (checkEmpty(endDateString) || checkEmpty(endDateString[0])) {
            errors.put(ParameterName.END_DATE, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                endDate = LocalDate.parse(endDateString[0]);

                if (endDate.isBefore(minDate)) {
                    errors.put(ParameterName.END_DATE, resourceManager.getValue(LocaleMessage.ERROR_COURSE_DATE_BEFORE_MIN));
                } else if (endDate.isAfter(maxDate)) {
                    errors.put(ParameterName.END_DATE, resourceManager.getValue(LocaleMessage.ERROR_COURSE_DATE_AFTER_MAX));
                } else if (!errors.containsKey(ParameterName.START_DATE)) {
                    String startDateStr = parameterMap.get(ParameterName.START_DATE)[0];
                    LocalDate startDate = LocalDate.parse(startDateStr);

                    if (endDate.isBefore(startDate)) {
                        errors.put(ParameterName.END_DATE, resourceManager.getValue(LocaleMessage.ERROR_END_DATE_AFTER_START));
                    }
                }
            } catch (DateTimeParseException e) {
                LOGGER.error("Error while parsing date " + endDateString[0]);
                errors.put(ParameterName.END_DATE, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_DATE));
            }
        }

        return this;
    }

    public CourseValidator validateStudentsLimit() {
        String[] limitValues = parameterMap.get(ParameterName.STUDENTS_LIMIT);

        if (checkEmpty(limitValues) || checkEmpty(limitValues[0])) {
            errors.put(ParameterName.STUDENTS_LIMIT, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else {
            try {
                int limit = Integer.parseInt(limitValues[0]);
                if (limit < MINIMAL_STUDENTS_LIMIT || limit > MAXIMAL_STUDENTS_LIMIT) {
                    errors.put(ParameterName.STUDENTS_LIMIT, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_STUDENTS_LIMIT));
                }
            } catch (NumberFormatException e) {
                LOGGER.error("Error while parsing students limit: " + limitValues[0], e);
                errors.put(ParameterName.STUDENTS_LIMIT, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_STUDENTS_LIMIT));
            }
        }

        return this;
    }

    public boolean checkEmpty(String line) {
        return line == null || line.isEmpty();
    }

    public boolean checkEmpty(String[] parameterValues) {
        return parameterValues == null;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
