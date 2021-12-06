package com.lilwel.elearning.AssignmentSubmission;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.validation.constraints.NotEmpty;
import java.util.UUID;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AssignmentSubmission {
    @Id
    UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private Double points=0d;
    @NotEmpty
    @Column(nullable = false)
    private String fileUrl;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private long submittedAt;
    @JsonIgnore
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="assignment_id",referencedColumnName = "id")
    private Assignment assignment;
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="student_id",referencedColumnName = "id")
    private Account student;


}
