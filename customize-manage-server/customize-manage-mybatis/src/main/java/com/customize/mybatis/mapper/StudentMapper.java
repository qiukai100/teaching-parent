package com.customize.mybatis.mapper;

import com.customize.mybatis.core.BaseMapper;
import com.customize.domain.entity.tb.Student;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {

    List<Student> queryStudentPage(Student student);
}