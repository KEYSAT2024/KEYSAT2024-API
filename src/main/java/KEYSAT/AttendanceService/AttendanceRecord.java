package KEYSAT.AttendanceService;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AttendanceRecord {
    @Id
    private Long id;
    private Long studentId;
    private Long courseId;
    private Date date;

    // Constructors
    public AttendanceRecord() {
    }

    public AttendanceRecord(Long id, Long studentId, Long courseId, Date date) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
