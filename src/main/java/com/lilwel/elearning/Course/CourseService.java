package com.lilwel.elearning.Course;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Account.AccountService;
import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.Assignment.AssignmentService;
import com.lilwel.elearning.CourseStudent.CourseStudent;
import com.lilwel.elearning.CourseStudent.CourseStudentKey;
import com.lilwel.elearning.CourseStudent.CourseStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AssignmentService assignmentService;

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }
    public Course addStudentToCourse(UUID courseId,Account student){
        Course course = getCourseById(courseId);
        Account acc = accountService.loadAccountByEmail(student.getEmail());
        CourseStudent courseStudent =CourseStudent.builder().course(course).student(acc).id(new CourseStudentKey(course.getId(),acc.getId())).build();
        courseStudentRepository.save(courseStudent);
        course.getCourseStudents().add(courseStudent);
        return courseRepository.save(course);
    }
    public List<Course> getAllInstructorCourses(UUID id){
        return courseRepository.findAllByInstructor(id);
    }
    public List<Course> getAllStudentCourses(UUID id){
        return courseRepository.findAllByStudent(id);
    }
    public List<String> getAllCourseStudents(UUID id){
        log.info(id.toString());
        return courseRepository.findAllCourseStudents(id);
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
        assignment.setCourse(course);
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        return assignment;
    }
}
