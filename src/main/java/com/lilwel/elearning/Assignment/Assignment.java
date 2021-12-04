package com.lilwel.elearning.Assignment;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Assignment")
@Table(
        name = "assignment"
)
@EntityListeners(AuditingEntityListener.class)
public class Assignment {
    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private UUID id = UUID.randomUUID();
    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String title;
    @Column(
            columnDefinition = "TEXT"
    )
    @Getter
    @Setter
    private String description;
    @Column(
            nullable = false
    )
    @Getter
    @Setter
    @NonNull
    private Double points;
    @Column(
            nullable = false
    )
    @Getter
    @Setter
    @NonNull
    private UUID instructorId;
    @Column(
            nullable = false
    )
    @Getter
    @Setter
    @NonNull
    private UUID courseId;
    @Column(
            nullable = false,
            updatable = false
    )
    @CreatedDate
    @Getter private long createdAt;
    @Column
    @Getter
    @Setter private long deadline;


    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", instructorId=" + instructorId +
                ", courseId=" + courseId +
                ", createdAt=" + createdAt +
                ", deadline=" + deadline +
                '}';
    }
}
