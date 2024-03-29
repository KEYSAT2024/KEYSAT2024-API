package keysat.service;

import keysat.entities.Course;
import keysat.entities.Instructor;
import keysat.repository.CourseRepository;
import keysat.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }

    public List<Instructor> getAllInstructors() {
        return this.instructorRepository.findAll();
    }

    public Optional<Instructor> getInstructorById(Long id) {
        return this.instructorRepository.findById(id);
    }

    public Collection<Course> getCoursesByInstructorId(Long id) {
        Optional<Instructor> instructorOptional = this.instructorRepository.findById(id);

        return instructorOptional.map(this.courseRepository::findByInstructor).orElse(null);
    }

    public Instructor addInstructor(Instructor instructor) {
        return this.instructorRepository.save(instructor);
    }

    public ResponseEntity<Instructor> updateInstructorById(Long id, Instructor updatedInstructor) {
        Optional<Instructor> instructorOptional = this.instructorRepository.findById(id);

        if (instructorOptional.isPresent()) {
            Instructor existedInstructor = instructorOptional.get();

            existedInstructor.setUsername(updatedInstructor.getUsername());

            return new ResponseEntity<>(this.instructorRepository.save(existedInstructor), HttpStatus.OK);
        }

        return new ResponseEntity<>(this.instructorRepository.save(updatedInstructor), HttpStatus.CREATED);
    }

    public void deleteInstructorById(Long id) {
        this.instructorRepository.deleteById(id);
    }
}
