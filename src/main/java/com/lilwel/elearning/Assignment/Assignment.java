package com.lilwel.elearning.Assignment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lilwel.elearning.AssignmentSubmission.AssignmentSubmission;
import com.lilwel.elearning.Course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty
    @Column(nullable = false)
    private String title;
    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotNull
    private Double points;


    @Column(nullable = false, updatable = false)
    @CreatedDate
    private long createdAt;
    @NotNull
    @Column
    private long deadline;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "assignment"
    )
    private List<AssignmentSubmission> submissions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

}
