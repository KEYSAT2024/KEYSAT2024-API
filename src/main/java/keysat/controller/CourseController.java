package keysat.controller;

import keysat.entities.Course;
import keysat.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return this.courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return this.courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Course addCourse(
            @RequestBody Course course,
            @RequestParam(name = "instructorId", required = false) Long instructorId
    ) {
        return this.courseService.addCourse(course, instructorId);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course updatedCourse,
            @RequestParam(name = "instructorId", required = false) Long instructorId
    ) {
        return this.courseService.updateCourse(id, updatedCourse, instructorId);
    }

    @DeleteMapping("{id}")
    public void deleteCourse(@PathVariable Long id) {
        this.courseService.deleteCourseById(id);
    }
}