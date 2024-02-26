package keysat.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;

@Entity(name = "instructor")
@Table(name = "instructor")
@PrimaryKeyJoinColumn(
        name = "instructor_id",
        referencedColumnName = "user_id",
        foreignKey = @ForeignKey(name = "instructor_user_id_fkey")
)
public class Instructor extends User {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "instructor_id",
            referencedColumnName = "instructor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "course_instructor_id_fkey")
    )
    private final Collection<Course> courseCollection = new HashSet<>();
}
