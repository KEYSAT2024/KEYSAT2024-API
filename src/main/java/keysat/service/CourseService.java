package keysat.service;

import keysat.entities.Course;
import keysat.entities.Instructor;
import keysat.repository.CourseRepository;
import keysat.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return this.courseRepository.findById(id);
    }

    public Course addCourse(Course course, Long instructorId) {
        if (instructorId != null) {
            Optional<Instructor> instructorOptional = this.instructorRepository.findById(instructorId);

            instructorOptional.ifPresent(course::setInstructor);
        }

        return this.courseRepository.save(course);
    }

    public ResponseEntity<Course> updateCourse(Long id, Course updatedCourse, Long instructorId) {
        Optional<Course> courseOptional = this.courseRepository.findById(id);
        Optional<Instructor> instructorOptional = Optional.empty();

        if (instructorId != null) instructorOptional = this.instructorRepository.findById(instructorId);

        if (courseOptional.isPresent()) {
            Course existedCourse = courseOptional.get();

            instructorOptional.ifPresent(existedCourse::setInstructor);
            existedCourse.setName(updatedCourse.getName());

            return new ResponseEntity<>(this.courseRepository.save(existedCourse), HttpStatus.OK);
        }

        instructorOptional.ifPresent(updatedCourse::setInstructor);

        return new ResponseEntity<>(this.courseRepository.save(updatedCourse), HttpStatus.CREATED);
    }

    public void deleteCourseById(Long id) {
        this.courseRepository.deleteById(id);
    }
}
