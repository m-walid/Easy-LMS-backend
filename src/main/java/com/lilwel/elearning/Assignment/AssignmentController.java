package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.Security.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@Slf4j
@RequestMapping(path = "api/v1/assignments")
public class AssignmentController {
    @Autowired
    private final AssignmentService assignmentService;


    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAssignments() {
        Map<String,String> user = (HashMap) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.get("userId"));
        return this.assignmentService.getAssignments();
    }

    @PostMapping
    public Assignment addAssignment() {
        return new Assignment();
    }
}
