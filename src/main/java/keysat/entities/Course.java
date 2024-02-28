package keysat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "course")
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "instructor_id",
            referencedColumnName = "instructor_id",
            foreignKey = @ForeignKey(name = "course_instructor_id_fkey")
    )
    private Instructor instructor;
}
