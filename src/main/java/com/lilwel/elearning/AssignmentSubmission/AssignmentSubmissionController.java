package com.lilwel.elearning.AssignmentSubmission;

import com.lilwel.elearning.Handlers.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/submissions")
public class AssignmentSubmissionController {
    @Autowired
    private final AssignmentSubmissionService assignmentSubmissionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable UUID id){
        try{
            AssignmentSubmission assignmentSubmission = assignmentSubmissionService.getSubmission(id);
            return ResponseHandler.handleResponse("Submission", HttpStatus.OK,assignmentSubmission);

        } catch(Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> gradeSubmission(@PathVariable UUID id, @RequestBody @Valid GradeDto gradeDto){
        try{
            AssignmentSubmission assignmentSubmission = assignmentSubmissionService.gradeSubmission(id,gradeDto.getPoints());
            return ResponseHandler.handleResponse("Submission graded", HttpStatus.OK,assignmentSubmission);

        } catch(Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
