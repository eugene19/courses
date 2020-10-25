package by.epamtc.courses.jsptag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class implements custom tag for select checkbox if array contain some value
 *
 * @author DEA
 */
public class ArraySearcher extends TagSupport {
    private static final long serialVersionUID = -5464536797253214363L;

    /**
     * Constant value of html attribute which show that value is checked
     */
    private static final String CHECKED = "checked";

    /**
     * Array of values to search
     */
    private String[] array;

    /**
     * Value to search
     */
    private String findValue;

    /**
     * Getting array of values to search
     *
     * @return array of values to search
     */
    public String[] getArray() {
        return array;
    }

    /**
     * Set new array of values to search
     *
     * @param array new array of values to search
     */
    public void setArray(String[] array) {
        this.array = array;
    }

    /**
     * Getting value to search
     *
     * @return value to search
     */
    public String getFindValue() {
        return findValue;
    }

    /**
     * Set new value to search
     *
     * @param findValue new value to search
     */
    public void setFindValue(String findValue) {
        this.findValue = findValue;
    }

    /**
     * Check if array contain search value. If contain print 'checked'
     *
     * @return integer value characterizing further action with the body of the custom tag
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if (array != null && Arrays.asList(array).contains(findValue)) {
                out.print(CHECKED);
            }
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
