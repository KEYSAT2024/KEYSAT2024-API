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
    @OneToMany(
            mappedBy = "instructor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private final Collection<Course> courseCollection = new HashSet<>();
}
