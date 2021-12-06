package com.lilwel.elearning.CourseStudent;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="course_students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CourseStudent {
    @EmbeddedId
    private CourseStudentKey id;
//    @Id
//    UUID id = UUID.randomUUID();
    @Column(updatable = false)
    @CreatedDate
    private long enrolledAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("studentId")
    @JoinColumn(referencedColumnName = "id")
    private Account student;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("courseId")
    @JoinColumn(referencedColumnName = "id")
    private Course course;

}
