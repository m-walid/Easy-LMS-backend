package com.lilwel.elearning.Course;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "tile can not be null")
    private String title;
    @NotNull(message = "description can not be null")
    private String description;

//    @OneToOne
//    @JoinColumn(name = "instructor_id")
//    private Account instructor;

//    @OneToMany(mappedBy = "course")
//    private List<Account> students;

//    @OneToMany(mappedBy = "course")
//    private List<Assignment> assignments;
}
