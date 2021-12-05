package com.lilwel.elearning.Account;


import com.lilwel.elearning.Course.Course;
import com.lilwel.elearning.CourseStudent.CourseStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "account",
        uniqueConstraints = @UniqueConstraint(name = "EmailUnique", columnNames = "email")
)
public class Account {
    public enum Role {
        STUDENT,
        Teacher
    }

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(nullable = false, name = "email")
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "course")
    private List<CourseStudent> courseStudents;
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> createdCourses;


}
