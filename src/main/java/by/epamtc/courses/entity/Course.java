package by.epamtc.courses.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class entity of Course
 *
 * @author DEA
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1383914108389031021L;

    /**
     * Unique identifier of course
     */
    private int id;

    /**
     * Summary of course
     */
    private String summary;

    /**
     * Description of course
     */
    private String description;

    /**
     * Material's path of course
     */
    private String materialPath;

    /**
     * Start date of course
     */
    private LocalDate startDate;

    /**
     * End date of course
     */
    private LocalDate endDate;

    /**
     * Limit of students on course
     */
    private int studentsLimit;

    /**
     * Identifier of course's lecturer (of <code>User</code> object)
     */
    private int lecturerId;

    /**
     * Status of course
     */
    private CourseStatus status;

    /**
     * Construct empty course
     */
    public Course() {
    }

    /**
     * @return unique identifier of course
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier to course
     *
     * @param id new value of unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return summary of course
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set summary to course
     *
     * @param summary new value of summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return description of course
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description to course
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return material path of course
     */
    public String getMaterialPath() {
        return materialPath;
    }

    /**
     * Set material path to course
     *
     * @param materialPath new value of material path
     */
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    /**
     * @return start date of course
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set start date to course
     *
     * @param startDate new value of start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end date of course
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Set end date to course
     *
     * @param endDate new value of end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return limit of students on course
     */
    public int getStudentsLimit() {
        return studentsLimit;
    }

    /**
     * Set limit of students on course
     *
     * @param studentsLimit new value of limit of students on course
     */
    public void setStudentsLimit(int studentsLimit) {
        this.studentsLimit = studentsLimit;
    }

    /**
     * @return id of lecturer of course
     */
    public int getLecturerId() {
        return lecturerId;
    }

    /**
     * Set lecturer id of course
     *
     * @param lecturerId new value of lecturer id of course
     */
    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    /**
     * @return status of course
     */
    public CourseStatus getStatus() {
        return status;
    }

    /**
     * Set status of course
     *
     * @param status new value of course status
     */
    public void setStatus(CourseStatus status) {
        this.status = status;
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
        Course course = (Course) o;
        return id == course.id &&
                studentsLimit == course.studentsLimit &&
                lecturerId == course.lecturerId &&
                Objects.equals(summary, course.summary) &&
                Objects.equals(description, course.description) &&
                Objects.equals(materialPath, course.materialPath) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate) &&
                status == course.status;
    }

    /**
     * Calculate hash code of object
     *
     * @return hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, summary, description, materialPath, startDate, endDate, studentsLimit, lecturerId, status);
    }

    /**
     * Make string representation of this course
     *
     * @return string representation of this course
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", materialPath='" + materialPath + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", studentsLimit=" + studentsLimit +
                ", lecturerId=" + lecturerId +
                ", status=" + status +
                '}';
    }
}
