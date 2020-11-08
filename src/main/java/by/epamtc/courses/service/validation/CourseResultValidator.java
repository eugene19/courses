package by.epamtc.courses.service.validation;

import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.service.i18n.LocaleMessage;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;

/**
 * Class for checking values of <code>CourseResult</code> attributes
 *
 * @author DEA
 */
public class CourseResultValidator extends AbstractValidator {
    private static final Logger LOGGER = Logger.getLogger(CourseResultValidator.class);

    /**
     * Constant containing maximal allowed mark
     */
    private static final int MAXIMAL_MARK = 10;

    /**
     * Constant containing minimal allowed mark
     */
    private static final int MINIMAL_MARK = 0;

    /**
     * Pattern of valid comment
     */
    private static final String COMMENT_PATTERN = ".{3,500}";

    /**
     * Construct a CourseResultValidator
     *
     * @param parameterMap request's parameters with values from client
     * @param locale       locale of client to select translation of error messages
     */
    public CourseResultValidator(Map<String, String[]> parameterMap, Locale locale) {
        super(parameterMap, locale);
    }

    /**
     * Check if value of mark is valid
     *
     * @return instance of validator object (this)
     */
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

    /**
     * Check if value of description is valid
     *
     * @return instance of validator object (this)
     */
    public CourseResultValidator validateDescription() {
        String[] commentValues = parameterMap.get(ParameterName.COMMENT);

        if (checkEmpty(commentValues) || checkEmpty(commentValues[0])) {
            errors.put(ParameterName.COMMENT, resourceManager.getValue(LocaleMessage.ERROR_FIELD_EMPTY));
        } else if (!commentValues[0].matches(COMMENT_PATTERN)) {
            errors.put(ParameterName.COMMENT, resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_COMMENT));
        }

        return this;
    }
}
