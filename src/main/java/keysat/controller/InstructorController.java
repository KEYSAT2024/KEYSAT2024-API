package keysat.controller;

import keysat.entities.Course;
import keysat.entities.Instructor;
import keysat.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return this.instructorService.getAllInstructors();
    }

    @GetMapping("{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        return this.instructorService.getInstructorById(id)
                .map(instructor -> new ResponseEntity<>(instructor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{id}/course")
    public Collection<Course> getCoursesByInstructorId(@PathVariable Long id) {
        return this.instructorService.getCoursesByInstructorId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Instructor addInstructor(@RequestBody Instructor instructor) {
        return this.instructorService.addInstructor(instructor);
    }

    @PutMapping("{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor updatedInstructor) {
        return this.instructorService.updateInstructorById(id, updatedInstructor);
    }

    @DeleteMapping("{id}")
    public void deleteInstructor(@PathVariable Long id) {
        this.instructorService.deleteInstructorById(id);
    }
}
