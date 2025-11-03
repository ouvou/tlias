package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.*;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 分页查询
     * 当路径参数名和形参对象的属性名一致时，可以省略@RequestParam注解。
     * Spring会自动将请求参数绑定到同名的属性上。
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除学生
     * Path 参数
     */
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("删除员工：{}", ids);
        studentService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 添加学生
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("新增学员：{}", student);
        studentService.save(student);
        return Result.success();
    }

    /**
     * 根据id查询学生信息
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        log.info("根据id查询学生信息：{}", id);
        Student student = studentService.selectById(id);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 违纪处理
     * 接口描述：该接口用于修改学员的数据信息
     */
    @Log
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("学生的违纪处理：id: {},score: {}",id,score);
        String scoreStr = score.toString();
        if (score <= 0 || score > 255) {
            return Result.error("违纪分数必须在1-255之间");
        }
        studentService.updateViolation(id,score);
        return Result.success();
    }

}
