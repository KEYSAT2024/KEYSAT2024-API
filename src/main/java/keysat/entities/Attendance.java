package keysat.entities;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "attendance")
@Table(name = "attendance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    private Long id;
    private Long studentId;
    private Long courseId;
    private Date date;

}
