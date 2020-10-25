package by.epamtc.courses.jsptag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Class implements custom tag for cutting long text
 *
 * @author DEA
 */
public class TextCutTag extends TagSupport {
    private static final long serialVersionUID = 254400180801394636L;

    /**
     * Constant value of ellipsis when cutting long text
     */
    private static final String ELLIPSIS = "...";

    /**
     * Field which contains original text
     */
    private String text;

    /**
     * Field which contains maximal length of outgoing text
     */
    private int maxLength;

    /**
     * Getting original text
     *
     * @return original text
     */
    public String getText() {
        return text;
    }

    /**
     * Set new original text
     *
     * @param text new original text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getting max length of text
     *
     * @return max length of text
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Set new max length of text
     *
     * @param maxLength max length of text
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Trims with ellipsis if the source text is longer than the maximum length
     *
     * @return integer value characterizing further action with the body of the custom tag
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if (text.length() > maxLength) {
                out.print(text.substring(0, maxLength) + ELLIPSIS);
            } else {
                out.print(text);
            }
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
