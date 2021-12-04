package com.lilwel.elearning.Course;

import com.lilwel.elearning.Handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Object> getCourses(){
        try {
            List<Course> courses = courseService.getAllCourses();
            return ResponseHandler.handleResponse("Successfully get all courses", HttpStatus.OK,courses);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
