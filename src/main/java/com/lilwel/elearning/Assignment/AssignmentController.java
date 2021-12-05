package com.lilwel.elearning.Assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAssignments() {
        return this.assignmentService.getAssignments();
    }

    @PostMapping
    public Assignment addAssignment(@RequestBody Assignment assignment) {
        return assignmentService.addAssignment(assignment);
    }
}
