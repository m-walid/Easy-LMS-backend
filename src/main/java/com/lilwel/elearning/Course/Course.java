package com.lilwel.elearning.Course;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.CourseStudent.CourseStudent;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Course {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    @NotNull(message = "tile can not be null")
    private String title;

    @NotNull(message = "description can not be null")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Account instructor;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "course")
    private List<Assignment> assignments;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "student")
    private List<CourseStudent> courseStudents;

}