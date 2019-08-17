package com.eagle.api;

import com.eagle.entity.Grade;
import com.eagle.entity.Student;

/**
 * @author qinlinsen
 */
public interface StudentService {
    /**
     *学生的成绩
     * @param student
     * @return
     */
    Grade student(Student student);
}
