package by.epamtc.courses.entity;

import java.io.Serializable;
import java.util.Objects;

public class CourseResult implements Serializable {
    private static final long serialVersionUID = -2876084831222741139L;

    private int mark;
    private String comment;

    public CourseResult() {
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResult that = (CourseResult) o;
        return mark == that.mark &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, comment);
    }

    @Override
    public String toString() {
        return "CourseResult{" +
                "mark=" + mark +
                ", comment='" + comment + '\'' +
                '}';
    }
}
