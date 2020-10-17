package by.epamtc.courses.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1383914108389031021L;

    private int id;
    private String summary;
    private String description;
    private String materialPath;
    private LocalDate startDate;
    private LocalDate endDate;
    private int studentsLimit;
    private int lecturerId;
    private CourseStatus status;

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getStudentsLimit() {
        return studentsLimit;
    }

    public void setStudentsLimit(int studentsLimit) {
        this.studentsLimit = studentsLimit;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, summary, description, materialPath, startDate, endDate, studentsLimit, lecturerId, status);
    }

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
