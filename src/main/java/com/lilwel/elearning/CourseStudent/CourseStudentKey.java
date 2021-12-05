package com.lilwel.elearning.CourseStudent;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class CourseStudentKey implements Serializable {

    private UUID courseId;
    private UUID studentId;

}
