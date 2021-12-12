package com.lilwel.elearning.Account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lilwel.elearning.Course.Course;
import com.lilwel.elearning.CourseStudent.CourseStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
        INSTRUCTOR
    }

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();
    @Size(min = 3, message = "name should be 3 letters or more")
    private String name;

    @Column(nullable = false, name = "email")
    @Email(message = "please enter a valid email")
    private String email;
    @Column(name = "password_hash", nullable = false)
    @Size(min=8,message = "password should be at least 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<CourseStudent> courseStudents;
    @JsonIgnore
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Course> createdCourses;


}
