package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ClazzMapper clazzMapper;

    /**
     * 基于pagehelper插件实现分页查询
     * @param studentQueryParam 查询参数
     * @return  PageResult<Student> 查询结果
     */
    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        //1.设置分页查询参数
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        //2.执行查询
        List<Student> stuList = studentMapper.list(studentQueryParam);
        //3.解析查询结果，并封装
        Page<Student> page = (Page<Student>) stuList;
        //log.info("数据库传递过来的数据：{}",page);
        return new PageResult<Student>(page.getTotal(),page.getResult());
    }

    /**
     * 根据ID删除学员
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        // 根据id删除学生信息
        studentMapper.deleteByIds(ids);
    }

    /**
     * 添加学员
     */
    @Override
    public void save(Student student) {
        //1.补全基本属性
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());

        //2.保存学员信息
        studentMapper.insert(student);
    }

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @Override
    public Student selectById(Integer id) {
        //根据学生id获取学生信息
        Student student = studentMapper.selectById(id);
        Integer clazzId = student.getClazzId();
        //根据班级id获取班级信息
        Clazz clazz = clazzMapper.queryById(clazzId);
        String clazzName = clazz.getName();

        //将班级名称封装到学生对象中
        student.setClazzName(clazzName);

        return student;
    }

    /**
     * 修改学生信息
     * @param student
     */
    @Override
    public void update(Student student) {
        //配置基本信息
        student.setUpdateTime(LocalDateTime.now());
        //再根据ID修改员工的基本信息
        studentMapper.updateById(student);
    }


    /**
     * 违纪处理
     */
    @Override
    public void updateViolation(Integer id, Integer score) {
        //首先根据ID获取学生对象
        Student student = selectById(id);
        Short violationCount = student.getViolationCount();
        Short violationScore = student.getViolationScore();

        //更新对象基本数据
        student.setUpdateTime(LocalDateTime.now());

        if (score>0){
            //每一次违纪处理 , 就需要将违纪次数往上累加一次
            //每一次违纪处理 , 需要将违纪分数累加
            //并将修改好的数据封装给对象
            student.setViolationCount((short) (student.getViolationCount() + 1));
            student.setViolationScore((short) (student.getViolationScore() + score));

            //更新数据库记录
            studentMapper.updateById(student);
            log.info("违纪处理完成，学生ID: {}, 处理后违纪次数: {}, 违纪分数: {}",
                    id, violationCount, violationScore);
        }else {
            log.warn("违纪分数无效，学生ID: {}, 分数: {}", id, score);
        }
    }


}
