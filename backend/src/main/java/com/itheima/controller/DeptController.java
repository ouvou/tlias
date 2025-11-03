package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Lombok提供的注解，用于自动生成日志记录器(Logger)对象
@Slf4j
//一个完整的请求路径 = 类上（公共的路径）注解的value属性值 + 方法（特有路径）注解上的value属性值
//Controller层关于公共路径的抽取
@RequestMapping("/depts")
@RestController
public class DeptController {
    // 定义日志记录器，关联当前类DeptController，用于输出日志
    //private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    //method属性：指定请求方式
    //value属性：指定请求的资源路径（URL）
    //@RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping//默认使用 GET 请求方式
    public Result list(){
        //System.out.println("查询所有部门数据");
        log.info("查询所有部门数据");
        List<Dept> list = deptService.findAll();
        return Result.success(list);
    }

    /**
     * 删除部门 - 方式一：（JavaEE）基于HttpServletRequest 获取请求参数
     */
    /*@DeleteMapping("/depts")
    public Result delete(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        System.out.println("根据ID删除部门："+id);
        return Result.success();
    }*/

    /**
     * 删除部门 - 方式二：（Spring）基于@RequestParam注解 获取请求参数
     * 注意事项：@RequestParam注解中的属性值required 默认为true
     * 代表该参数必须传递，如果不传递将报错，如果参数可选，可以将属性设置为false。
     */
/*
    @DeleteMapping("/depts")
    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){
        System.out.println("根据ID删除部门："+deptId);
        return Result.success();
    }
*/

    /**
     * 删除部门 - 方式三：省略@RequestParam注解
     * 使用前提：前端传递的请求参数名与服务端方法形参名一致   [推荐]
     * 当方法参数名与请求参数名一致时，Spring 自动绑定，未传递参数则值为 null，不报错。
     */
    @Log
    @DeleteMapping
    public Result delete(Integer id) {
        //System.out.println("根据ID删除部门："+id);
        log.info("根据ID删除部门：{}",id);
        try {
            deptService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增部门
     * @RequestBody： JSON格式的参数，通常会使用一个实体对象进行接收
     * 规则：JSON数据的键名与方法形参对象的属性名相同，并需要使用@RequestBody注解标识。
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        //System.out.println("添加部门:"+dept);
        log.info("添加部门:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门
     * @PathVariable 注解用于将 URL 中的占位符 {id} 绑定到方法参数 deptId 上，实现路径变量的映射。
     * 简单理解：@PathVariable注解来声明获取的是路径参数
     */
    /*@GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){
        System.out.println("根据ID查询部门 : "+deptId);
        return Result.success();
    }*/

    /**
     * 根据ID查询部门
     * @PathVariable 注解用于将 URL 中的占位符 {id} 绑定到方法参数 deptId 上，实现路径变量的映射。
     * 当方法的形参名与路径参数的参数名一致时，可以省去@PathVariable注解的value属性值
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        //System.out.println("根据ID查询部门 : "+id);
        log.info("根据ID查询部门 : {}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        //System.out.println("修改部门："+dept);
        log.info("修改部门：{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
