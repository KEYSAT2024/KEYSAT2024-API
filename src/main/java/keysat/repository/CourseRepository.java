package keysat.repository;

import keysat.entities.Course;
import keysat.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Collection<Course> findByInstructor(Instructor instructor);
}
