package com.lilwel.elearning.AssignmentSubmission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionKey implements Serializable {
    private UUID studentId;
    private UUID assignmentId;
}
