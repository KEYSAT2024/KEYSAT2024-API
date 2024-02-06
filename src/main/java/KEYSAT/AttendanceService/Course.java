package KEYSAT.AttendanceService;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    private Long id;
    private String name;
    private String instructor;

    // Constructors
    public Course() {
    }

    public Course(Long id, String name, String instructor) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
