package com.eagle.api.impl;

import com.eagle.api.StudentService;
import com.eagle.entity.Grade;
import com.eagle.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author qinlinsen
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public Grade student(Student student) {
        Assert.notNull(student, "student is required");
        Grade grade = new Grade();
        grade.setChineseScore(12.09);
        grade.setMathScore(19.09);
        return grade;
    }
}
