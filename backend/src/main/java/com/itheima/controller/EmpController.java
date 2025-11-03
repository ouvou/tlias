package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.*;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
//Controller层关于公共路径的抽取
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    EmpService empService;


    /**
     * 分页查询
     *
     * @@DateTimeFormat: 日期时间类型参数接收时，需要通过@DateTimeFormat注解指定前端传递的日期格式
     */
    /*@GetMapping
    //通过@RequestParam注解的defaultValue属性可以设置参数的默认值
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,Integer gender,
                       @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate end
    ){
       log.info("分页查询：{},{},{},{},{},{}",page,pageSize,name,gender,begin,end);
       PageResult<Emp> pageResult = empService.page(page,pageSize,name,gender,begin,end);
       return Result.success(pageResult);
    }*/
    @GetMapping
    //通过定义一个对象，来接收参数
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询：{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 查询全部员工
     * 请求路径：/emps/list
     * 请求方式：GET
     * 接口描述：该接口用于查询全部员工（班主任）信息
     */
    @GetMapping("/list")
    public Result findAll() {
        log.info("查询全部员工（班主任）信息");
        List<Emp> list = empService.findAll();
        return Result.success(list);
    }


    /**
     * 新增员工
     *
     * @param emp
     * @return
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp) throws Exception {
        log.info("新增员工：{}", emp);
        try {
            empService.save(emp);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error("用户名 '" + emp.getUsername() + "' 已存在");
        }
    }

    /**
     * 删除员工 - 数组
     */
/*
    @DeleteMapping
    public Result delete(Integer[] ids){
        log.info("删除员工：{}", Arrays.toString(ids));
        return Result.success();
    }
*/

    /**
     * 删除员工 - List
     * Query 参数
     */
    @Log
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工：{}", ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 根据ID查询员工信息及员工的工作经历信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据ID查询员工信息：{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 根据ID更新员工信息及员工的工作经历信息
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("根据ID更新员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }


    /**
     * 修改员工密码
     */
    @Log
    @PutMapping("/password")
    public Result updatePassword(@RequestBody UserPassword userPassword) {
        log.info("修改密码：{}", userPassword);
        try {
            empService.updatePassword(userPassword);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

}
