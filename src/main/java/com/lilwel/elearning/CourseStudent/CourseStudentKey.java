package com.lilwel.elearning.CourseStudent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentKey implements Serializable {

    private UUID courseId;
    private UUID studentId;

}
