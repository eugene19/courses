package by.epamtc.courses.jsptag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TextCutTag extends TagSupport {
    private static final long serialVersionUID = 254400180801394636L;

    private static final String ELLIPSIS = "...";

    private String text;
    private int maxLength;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

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
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
