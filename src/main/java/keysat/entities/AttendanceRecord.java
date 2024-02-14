package keysat.entities;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecord {
    @Id
    private Long id;
    private Long studentId;
    private Long courseId;
    private Date date;

}
