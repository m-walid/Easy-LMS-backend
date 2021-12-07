package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.AWS.AmazonS3FileService;
import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import com.lilwel.elearning.Security.AuthPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;
@Slf4j
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    private final AmazonS3FileService amazonS3FileService;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository, AmazonS3FileService amazonS3FileService) {
        this.assignmentRepository = assignmentRepository;
        this.amazonS3FileService = amazonS3FileService;
    }

    public Assignment getAssignment(UUID id) {
        return assignmentRepository.findById(id).get();
    }

    public void deleteAssignment(UUID id) {
        assignmentRepository.deleteById(id);
    }

    public AssignmentSubmission addAssignmentSubmissionToAssignment(UUID assignmentId, MultipartFile file) throws Exception {

        Assignment assignment= this.getAssignment(assignmentId);
        log.info("now = {} , deadline = {}",new Date().getTime(),assignment.getDeadline());
        if(new Date().getTime()/1000 > assignment.getDeadline() ){
            throw new Exception("Deadline expired");
        }
        String fileUrl = amazonS3FileService.uploadFileToAmazon(file);
        AssignmentSubmission assignmentSubmission =new AssignmentSubmission();
        assignmentSubmission.setFileUrl(fileUrl);
        assignmentSubmission.setFileName(file.getOriginalFilename());
        AuthPrincipal authPrincipal=(AuthPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assignmentSubmission.setStudent(Account.builder().id(authPrincipal.getUserId()).build());
        assignmentSubmission.setAssignment(assignment);
        assignment.getSubmissions().add(assignmentSubmission);
        assignmentRepository.save(assignment);
        assignmentSubmission.setFileUrl(null);
        return assignmentSubmission;
    }


}
