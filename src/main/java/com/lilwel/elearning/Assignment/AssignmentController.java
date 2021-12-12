package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmissionService;
import com.lilwel.elearning.Handlers.ResponseHandler;
import com.lilwel.elearning.Security.AuthPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@Slf4j
@RequestMapping(path = "api/v1/assignments")
public class AssignmentController {
    @Autowired
    private final AssignmentService assignmentService;
    @Autowired
    private final AssignmentSubmissionService assignmentSubmissionService;


    public AssignmentController(AssignmentService assignmentService, AssignmentSubmissionService assignmentSubmissionService) {
        this.assignmentService = assignmentService;
        this.assignmentSubmissionService = assignmentSubmissionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable UUID id) {
        try {
            Assignment assignment = assignmentService.getAssignment(id);
            return ResponseHandler.handleResponse("Assignment", HttpStatus.OK, assignment);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable UUID id) {
        try {
            assignmentService.deleteAssignment(id);
            return ResponseHandler.handleResponse("Assignment deleted", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}/submissions")
    public ResponseEntity<?> getAssignmentSubmissions(@PathVariable UUID id) {
        try {
            AuthPrincipal authPrincipal = (AuthPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<String> grantedAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (Objects.equals(grantedAuthority.get(0), Account.Role.INSTRUCTOR.toString())) {
                Assignment assignment = assignmentService.getAssignment(id);
                return ResponseHandler.handleResponse("Assignment submissions", HttpStatus.OK, assignment.getSubmissions());

            } else {
                AssignmentSubmission assignmentSubmission = assignmentSubmissionService.getStudentAssignmentSubmission(id,authPrincipal.getUserId());
                return ResponseHandler.handleResponse("Assignment submissions", HttpStatus.OK, assignmentSubmission);
            }

        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/submissions")
    public ResponseEntity<?> addAssignmentSubmission(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) throw new Exception("please submit a file");

            AssignmentSubmission assignmentSubmission = assignmentService.addAssignmentSubmissionToAssignment(id, file);

            return ResponseHandler.handleResponse("Assignment submission added", HttpStatus.OK, assignmentSubmission);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
