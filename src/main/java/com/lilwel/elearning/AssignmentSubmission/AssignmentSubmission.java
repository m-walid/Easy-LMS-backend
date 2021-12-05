package com.lilwel.elearning.AssignmentSubmission;


import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Assignment.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AssignmentSubmission {
    @EmbeddedId
    private AssignmentSubmissionKey id;

    @Column(nullable = false)
    private Double points;

    @Column(nullable = false)
    private String fileUrl;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private long submittedAt;

    @ManyToOne(optional = false)
    @MapsId("assignmentId")
    @JoinColumn(referencedColumnName = "id")
    private Assignment assignment;

    @ManyToOne(optional = false)
    @MapsId("studentId")
    @JoinColumn(referencedColumnName = "id")
    private Account student;




}
