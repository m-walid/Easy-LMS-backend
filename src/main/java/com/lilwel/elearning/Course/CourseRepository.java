package com.lilwel.elearning.Course;

import com.lilwel.elearning.Account.Account;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query(value = "select * from course where instructor_id= ?1",nativeQuery = true)
    List<Course> findAllByInstructor(UUID instructorId);

    @Query(value = "select * from course where id in (select course_id from course_students where student_id= ?1)",nativeQuery = true)
    List<Course> findAllByStudent(UUID studentId);

    @Query(value = "select email from account where id in (select student_id from course_students where course_id= ?1)",nativeQuery = true)
    List<String> findAllCourseStudents(UUID courseId);
}
