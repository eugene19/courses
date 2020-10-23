package by.epamtc.courses.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class entity of Result of course
 *
 * @author DEA
 */
public class CourseResult implements Serializable {
    private static final long serialVersionUID = -2876084831222741139L;

    /**
     * Unique identifier of result
     */
    private int id;

    /**
     * Mark of result
     */
    private int mark;

    /**
     * Comment of result
     */
    private String comment;

    /**
     * Construct empty course result
     */
    public CourseResult() {
    }

    /**
     * @return unique identifier of course result
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier to course result
     *
     * @param id new value of unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return mark of course result
     */
    public int getMark() {
        return mark;
    }

    /**
     * Set mark to course result
     *
     * @param mark new value of mark
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

    /**
     * @return comment of course result
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment to course result
     *
     * @param comment new value of comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Compares this object with another object for equality
     *
     * @param o the object with which to compare
     * @return true if objects are equal, else - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResult that = (CourseResult) o;
        return id == that.id &&
                mark == that.mark &&
                Objects.equals(comment, that.comment);
    }

    /**
     * Calculate hash code of object
     *
     * @return hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, mark, comment);
    }

    /**
     * Make string representation of this course result
     *
     * @return string representation of this course result
     */
    @Override
    public String toString() {
        return "CourseResult{" +
                "id=" + id +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                '}';
    }
}
