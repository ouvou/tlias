package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    /**
     * 条件分页查询
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 根据ID删除学生
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 添加学员
     */
    void save(Student student);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    Student selectById(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 违纪处理
     */
    void updateViolation(Integer id, Integer score);
}
