package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public Assignment getAssignment(UUID id) {
        return assignmentRepository.findById(id).get();
    }

    public void deleteAssignment(UUID id) {
        assignmentRepository.deleteById(id);
    }

    public AssignmentSubmission addAssignmentSubmissionToAssignment(UUID assignmentId, AssignmentSubmission assignmentSubmission){
        Assignment assignment= this.getAssignment(assignmentId);
        assignmentSubmission.setAssignment(assignment);
        assignment.getSubmissions().add(assignmentSubmission);
        assignmentRepository.save(assignment);
        return assignmentSubmission;
    }


}
