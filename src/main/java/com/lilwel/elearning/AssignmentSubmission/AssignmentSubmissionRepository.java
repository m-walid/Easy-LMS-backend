package com.lilwel.elearning.AssignmentSubmission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, UUID> {

    @Query(value = "select * from assignment_submission where assignment_id = ?1 and student_id= ?2",nativeQuery = true)
    AssignmentSubmission getStudentAssignmentSubmission(UUID assignmentId,UUID studentId);
}
