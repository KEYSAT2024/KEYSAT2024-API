The Keysat2024 API is a educational platform that manages attendance records, courses, instructors, and students. It provides a REST API to interact with the system, handling user authentication, role-based access control, and CRUD operations for various entities.

**Features**

- User authentication and authorization with Spring Security
- Role-based access control for different user types (e.g., students, instructors)
- Management of attendance records, courses, instructors, and students
- Secure password handling with BCrypt encryption
- RESTful API endpoints for CRUD operations

**Prerequisites**

    Java Development Kit (JDK) 8 or higher
    Maven for dependency management and project build
    A suitable Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse

**Setup and Installation**

    Clone the repository to your local machine.
    Navigate to the project's root directory.
    Run mvn clean install to build the project and install the dependencies.
    Set up the database using Spring Boot's capabilities and run the provided script located in /sql to insert user roles into the database.
    Configure the application.properties file in the src/main/resources directory with your specific datasource, port, and any other configurations.

**Running the Application**

    Execute the main method in the KeysatAlphaApplication class from your IDE

REST API Endpoints

     Authentication
    
        POST /auth/login: Authenticate a user.
        POST /auth/logout: Log out the current user.
    
    Attendance Records
    
        GET /attendance: Retrieve all attendance records.
        POST /attendance: Add a new attendance record.
        GET /attendance/{id}: Retrieve an attendance record by ID.
    
    Courses
    
        GET /courses: Retrieve all courses.
        POST /courses: Add a new course.
        GET /courses/{id}: Retrieve a course by ID.
    
    Instructors
    
        GET /instructors: Retrieve all instructors.
        POST /instructors: Add a new instructor.
        GET /instructors/{id}: Retrieve an instructor by ID.
        PUT /instructors/{id}: Update an instructor by ID.
        DELETE /instructors/{id}: Delete an instructor by ID.
    
    Students
    
        GET /students: Retrieve all students.
        POST /students: Add a new student.
        GET /students/{id}: Retrieve a student by ID.
    
    User Managment
    
        GET /users: Retrieve all users.
        POST /users/create: Create a new user.
        GET /users/{userId}: Retrieve a user by ID.
        PUT /users/{userId}: Update a user by ID.
        POST /users/change-password: Change a user's password.

Testing

    The src/test directory contains tests for the application.


Development Routes

    The TestRoutes.java class provides endpoints for internal development and testing. These routes are not intended for production use.
