package com.itheima.service;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /**
     * 新增班级
     */
    void add(Clazz clazz);

    /**
     * 查询所有班级信息
     */
    List<Clazz> findAll();

    /**
     * 分页查询班级
     * @param empQueryParam 查询条件
     * @return 查询结果
     */
    PageResult<Clazz> page(EmpQueryParam empQueryParam);

    /**
     * 根据ID删除班级
     * @param id 班级id
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询班级
     * @param id 班级id
     * @return Clazz对象
     */
    Clazz queryById(Integer id);

    /**
     * 修改班级
     * @param clazz
     */
    void update(Clazz clazz);
}
