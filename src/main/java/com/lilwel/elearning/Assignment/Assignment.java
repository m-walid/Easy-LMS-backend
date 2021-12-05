package com.lilwel.elearning.Assignment;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import com.lilwel.elearning.Course.Course;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "Assignment")
@Table(name = "assignment")
@EntityListeners(AuditingEntityListener.class)
public class Assignment {
    @Id
    @Column(name = "id", updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double points;


    @Column(nullable = false, updatable = false)
    @CreatedDate
    private long createdAt;

    @Column
    private long deadline;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "assignment"
    )
    private List<AssignmentSubmission> submissions;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

}
