package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequestMapping("/clazzs")//定义路径前缀，简化方法映射 将方法上的@XxxMapping注解的公共路径抽取到类上
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 班级列表数据的条件分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("班级列表数据 的 条件分页查询：{}",empQueryParam);
        PageResult<Clazz> page = clazzService.page(empQueryParam);
        return Result.success(page);
    }


    /**
     * 新增班级
     * 通过@RequestBody注解来获取前端传递的JSON数据
     * 并自动绑定到Clazz对象中
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Clazz clazz){
        //System.out.println("新增班级："+clazz);
        log.info("新增班级：{}",clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    /**
     * 查询班级
     * 查询回显
     */
    @GetMapping("/list")
    public Result list(){
        //System.out.println("查询所有班级信息");
        log.info("查询所有班级信息");
        List<Clazz> list = clazzService.findAll();
        return Result.success(list);
    }

    /**
     * 根据ID删除班级信息
     * @PathVariable 注解用于将 URL 中的占位符 {id} 绑定到方法参数 deptId 上，实现路径变量的映射。
     */
    @Log
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("根据ID删除班级：{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID查询班级信息
     */
    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        log.info("根据ID查询班级：{}",id);
        Clazz clazz = clazzService.queryById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级: {}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }

}
