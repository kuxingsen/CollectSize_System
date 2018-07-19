package com.yiban.mapper;

import com.yiban.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * 学生Mapper
 */
@Repository
public interface studentMapper {
    Student select(String id);
    int updateStudentBaseInfo(Student student);
    int insert(Student student);
}
