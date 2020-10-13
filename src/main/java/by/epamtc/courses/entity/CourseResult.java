package by.epamtc.courses.entity;

import java.io.Serializable;
import java.util.Objects;

public class CourseResult implements Serializable {
    private static final long serialVersionUID = -2876084831222741139L;

    private int id;
    private int mark;
    private String comment;

    public CourseResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == that.id &&
                mark == that.mark &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, comment);
    }

    @Override
    public String toString() {
        return "CourseResult{" +
                "id=" + id +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                '}';
    }
}
