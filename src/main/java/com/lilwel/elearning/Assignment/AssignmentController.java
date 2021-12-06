package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmissionKey;
import com.lilwel.elearning.Handlers.ResponseHandler;
import com.lilwel.elearning.Security.AuthPrincipal;
import com.lilwel.elearning.Security.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController()
@Slf4j
@RequestMapping(path = "api/v1/assignments")
public class AssignmentController {
    @Autowired
    private final AssignmentService assignmentService;


    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable UUID id) {
        try{
            Assignment assignment = assignmentService.getAssignment(id);
            return ResponseHandler.handleResponse("Assignment", HttpStatus.OK,assignment);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable UUID id) {
        try{
            assignmentService.deleteAssignment(id);
            return ResponseHandler.handleResponse("Assignment deleted", HttpStatus.OK,null);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping("/{id}/submissions")
    public ResponseEntity<?> getAssignmentSubmissions(@PathVariable UUID id){
        try{
            Assignment assignment = assignmentService.getAssignment(id);
            return ResponseHandler.handleResponse("Assignment submissions", HttpStatus.OK,assignment.getSubmissions());
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @PostMapping("/{id}/submissions")
    public ResponseEntity<?> addAssignmentSubmission(@PathVariable UUID id, @RequestBody @Valid AssignmentSubmission assignmentSubmission){
        try{
            AuthPrincipal authPrincipal=(AuthPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            assignmentSubmission.setStudent(Account.builder().id(authPrincipal.getUserId()).build());
//            assignmentSubmission.setId(new AssignmentSubmissionKey(authPrincipal.getUserId(),id));
            assignmentService.addAssignmentSubmissionToAssignment(id,assignmentSubmission);
            return ResponseHandler.handleResponse("Assignment submission added", HttpStatus.OK,assignmentSubmission);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
