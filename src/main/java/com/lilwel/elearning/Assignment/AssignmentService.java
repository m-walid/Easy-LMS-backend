package com.lilwel.elearning.Assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAssignments(){
    return assignmentRepository.findAll();
    }

    public Assignment addAssignment(Assignment assignment){
        return assignmentRepository.save(assignment);
    }

}
