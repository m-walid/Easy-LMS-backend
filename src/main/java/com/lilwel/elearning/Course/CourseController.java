package com.lilwel.elearning.Course;

import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.Handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> addCourse(@RequestBody @Valid Course course){
        try {
            Course addedCourse = courseService.addCourse(course);
            return ResponseHandler.handleResponse("Successfully add course", HttpStatus.OK,addedCourse);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getCourses(@RequestParam(required = false) UUID courseId){
        try {
            if(courseId==null){
                List<Course> courses = courseService.getAllCourses();
                return ResponseHandler.handleResponse("Successfully get all courses", HttpStatus.OK,courses);
            }else{
                Course course = courseService.getCourseById(courseId);
                return ResponseHandler.handleResponse("Successfully get the course", HttpStatus.OK,course);
            }
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID id){
        try {
            courseService.deleteCourse(id);
            return ResponseHandler.handleResponse("Successfully delete course", HttpStatus.OK,null);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping("/{courseId}/assignments")
    public ResponseEntity<Object> getCourseAssignments(@PathVariable UUID courseId){
        try {
            List<Assignment> assignments = courseService.getCourseAssignments(courseId);
            return ResponseHandler.handleResponse("Successfully get all course assignments",
                    HttpStatus.OK,assignments);

        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("/{courseId}/assignments")
    public ResponseEntity<Object> addCourseAssignment(@PathVariable UUID courseId,
                                                      @RequestBody Assignment assignment){
        try {
            Assignment savedAssignment = courseService.addCourseAssignment(courseId,assignment);
            return ResponseHandler.handleResponse("Successfully get all course assignments",
                    HttpStatus.OK,savedAssignment);

        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
