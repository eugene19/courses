package by.epamtc.courses.service.validation;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CourseResultValidator {
    private static final Logger LOGGER = Logger.getLogger(CourseResultValidator.class);

    private static final int MAXIMAL_MARK = 10;
    private static final int MINIMAL_MARK = 0;

    private static final String COMMENT_PATTERN = ".{3,500}";

    private Map<String, String[]> parameterMap;
    private Map<String, String> errors;
    private ResourceManager resourceManager;

    public CourseResultValidator(Map<String, String[]> parameterMap, Locale locale) {
        this.parameterMap = parameterMap;
        this.errors = new HashMap<>();
        this.resourceManager = new ResourceManager(locale);
    }

    public CourseResultValidator validateMark() {
        String[] markValues = parameterMap.get(ParameterName.MARK);

        if (checkEmpty(markValues) || checkEmpty(markValues[0])) {
            errors.put(ParameterName.MARK, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
            return this;
        }

        try {
            int mark = Integer.parseInt(markValues[0]);
            if (mark < MINIMAL_MARK || mark > MAXIMAL_MARK) {
                errors.put(ParameterName.MARK, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_MARK));
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Error while parsing mark: " + markValues[0], e);
            errors.put(ParameterName.MARK, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_MARK));
        }

        return this;
    }

    public CourseResultValidator validateDescription() {
        String[] commentValues = parameterMap.get(ParameterName.COMMENT);

        if (checkEmpty(commentValues) || checkEmpty(commentValues[0])) {
            errors.put(ParameterName.COMMENT, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!commentValues[0].matches(COMMENT_PATTERN)) {
            errors.put(ParameterName.COMMENT, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_COMMENT));
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
