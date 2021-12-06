package com.lilwel.elearning.Course;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.Handlers.ResponseHandler;
import com.lilwel.elearning.Security.AuthPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> addCourse(@RequestBody @Valid Course course) {
        try {
            AuthPrincipal authPrincipal = (AuthPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            course.setInstructor(Account.builder().id(authPrincipal.getUserId()).build());
            Course addedCourse = courseService.addCourse(course);
            return ResponseHandler.handleResponse("Successfully add course", HttpStatus.OK, addedCourse);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getCourses() {
        try {
            AuthPrincipal authPrincipal = (AuthPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<String> grantedAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            List<Course> courses;
            log.info("role = {}",grantedAuthority.get(0));
            log.info("role = {}",Account.Role.INSTRUCTOR.toString());
            if (Objects.equals(grantedAuthority.get(0), Account.Role.INSTRUCTOR.toString())) {
                log.info("in instructor");
                courses = courseService.getAllInstructorCourses(authPrincipal.getUserId());
            } else {
                log.info("in student");
                courses = courseService.getAllStudentCourses(authPrincipal.getUserId());
            }
            return ResponseHandler.handleResponse("Successfully get all courses", HttpStatus.OK, courses);

        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourse(@PathVariable UUID id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseHandler.handleResponse("Successfully get the course", HttpStatus.OK, course);

        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID id) {
        try {
            courseService.deleteCourse(id);
            return ResponseHandler.handleResponse("Successfully delete course", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{courseId}/assignments")
    public ResponseEntity<Object> getCourseAssignments(@PathVariable UUID courseId) {
        try {
            List<Assignment> assignments = courseService.getCourseAssignments(courseId);
            return ResponseHandler.handleResponse("Successfully get all course assignments",
                    HttpStatus.OK, assignments);

        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{courseId}/assignments")
    public ResponseEntity<Object> addCourseAssignment(@PathVariable UUID courseId,
                                                      @RequestBody @Valid Assignment assignment) {
        try {
            Assignment savedAssignment = courseService.addCourseAssignment(courseId, assignment);
            return ResponseHandler.handleResponse("Successfully get all course assignments",
                    HttpStatus.OK, savedAssignment);

        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<?> getCourseStudents(@PathVariable UUID id) {
        try {
            List<String> students = courseService.getAllCourseStudents(id);
            return ResponseHandler.handleResponse("All students", HttpStatus.OK, students);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<?> addCourseStudent(@PathVariable UUID id, @RequestBody Account student) {
        try {
            Course course = courseService.addStudentToCourse(id, student);
            return ResponseHandler.handleResponse("All students", HttpStatus.OK, course);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
