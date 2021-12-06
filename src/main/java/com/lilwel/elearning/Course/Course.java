package com.lilwel.elearning.Course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Assignment.Assignment;
import com.lilwel.elearning.CourseStudent.CourseStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "title can not be null")
    private String title;

    @NotBlank(message = "description can not be null")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Account instructor;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "course",cascade = CascadeType.ALL)
    private List<Assignment> assignments;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "student")
    private List<CourseStudent> courseStudents;

}
