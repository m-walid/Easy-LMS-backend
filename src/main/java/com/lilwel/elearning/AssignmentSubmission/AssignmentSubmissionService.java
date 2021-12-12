package com.lilwel.elearning.AssignmentSubmission;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionService {
    @Autowired
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;

    public AssignmentSubmission getSubmission(UUID id) {
        return assignmentSubmissionRepository.getById(id);
    }

    public AssignmentSubmission getStudentAssignmentSubmission(UUID assignmentId, UUID studentId) {
        return assignmentSubmissionRepository.getStudentAssignmentSubmission(assignmentId, studentId);
    }

    public AssignmentSubmission gradeSubmission(UUID id, Double points) throws Exception {
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.getById(id);
        if (points <= assignmentSubmission.getAssignment().getPoints()) {
            assignmentSubmission.setPoints(points);
            assignmentSubmissionRepository.save(assignmentSubmission);
            return assignmentSubmission;
        } else {
            throw new Exception("points can't be more than maximum points");
        }
    }
}
