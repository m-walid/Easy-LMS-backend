package com.lilwel.elearning.CourseStudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, UUID> {
}
