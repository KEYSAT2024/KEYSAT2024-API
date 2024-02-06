KEYSAT-API-EXPERIMENTAL is a Spring Boot application with Spring Security for secure web interactions. It includes user authentication and protected endpoints.
Configuration. This is an experimental build for researching and developing basic funcionality for the eventual KEYSAT2024 project.
Security Configuration (Auth.java)

    Endpoints:
        Public: "/", "/home", "/login"
        Secured: "/secret" *USER ROLE*
    Authentication:
        Type: Form-based
        Page: "/login"
    User Details:
        Stored In-memory
        Default User: "user", Password: "password"

Controllers

    API Controller (API.java):
        Handles requests for "/", "/login"
        Returns view names for Thymeleaf templates

Application Entry Point

    Class: KeysatAlphaApplication.java
    Purpose: Bootstraps the application

Development
Setup

    Add spring-boot-starter-security and spring-boot-starter-thymeleaf to your build configuration.
    Place Thymeleaf templates in src/main/resources/templates.

Security

    Use BCryptPasswordEncoder for production password encoding.
    Include CSRF token in login forms.
    Check session management settings if experiencing authentication issues.

Debugging

    Set logging.level.org.springframework.security=DEBUG for in-depth security logs.


Endpoints for Attendance Service:

    GET `/courses:` Lists all courses.
    GET `/courses/{id}`: Retrieves a specific course by ID.
    POST `/courses: `Creates a new course.
    PUT `/courses/{id`}: Updates an existing course by ID.
    DELETE `/courses/{id}`: Deletes a course by ID.    GET `/students`: Lists all students.
    GET `/students/{id}`: Retrieves a specific student by ID.
    POST `/students`: Creates a new student.
    PUT `/students/{id}`: Updates an existing student by ID.
    DELETE `/students/{id}`: Deletes a student by ID.    GET `/attendanceRecords:` Lists all attendance records.
    GET `/attendanceRecords/{id}:` Retrieves a specific attendance record by ID.
    POST `/attendanceRecords:` Creates a new attendance record.
    PUT `/attendanceRecords/{id}:` Updates an existing attendance record by ID.
    DELETE `/attendanceRecords/{id}:` Deletes an attendance record by ID.