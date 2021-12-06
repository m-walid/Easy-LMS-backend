package com.lilwel.elearning.Course;

import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.Assignment.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssignmentService assignmentService;

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public void deleteCourse(UUID id){
        courseRepository.deleteById(id);
    }

    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId).get();
    }

    public List<Assignment> getCourseAssignments(UUID courseId) {
        return courseRepository.findById(courseId).get().getAssignments();
    }

    public Assignment addCourseAssignment(UUID courseId,Assignment assignment) {
        Course course = courseRepository.findById(courseId).get();
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        return assignment;
    }
}
