package keysat.controller;

import keysat.repository.CourseRepository;
import keysat.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        String name = "course1";
        String instructor = "instructor1";
        String role = "ADMIN";

        // Create the course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"" + name + "\", \"instructor\": \"" + instructor + "\", \"role\": \"" + role + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /courses
        ResultActions resultActions = mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].instructor").exists());
    }


    @Test
    public void testGetCourseById() throws Exception {
        String name = "course1";
        String instructor = "instructor1";
        String role = "ADMIN";

        // Create the course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"" + name + "\", \"instructor\": \"" + instructor + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /courses/d}}
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.instructor").exists());
    }

    @Test
    public void testAddCourse() throws Exception {
        String name = "course1";
        String instructor = "instructor1";
        String role = "ADMIN";

        // Create the course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"" + name + "\", \"instructor\": \"" + instructor + "\", \"role\": \"" + role
                        + "\" }")
                .param("role", "ADMIN")).andExpect(status().isOk());

        // Perform GET request to /courses
        ResultActions resultActions = mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].instructor").exists());
    }
}
