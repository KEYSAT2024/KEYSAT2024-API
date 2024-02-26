package keysat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity(name = "student")
@Table(name = "student")
@PrimaryKeyJoinColumn(
        name = "student_id",
        referencedColumnName = "user_id",
        foreignKey = @ForeignKey(name = "student_user_id_fkey")
)
public class Student extends User {}