package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ClazzMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public void add(Clazz clazz) {
        //1. 补全基础属性 -createTime -updateTime
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        //2. 调用mapper接口插入数据
        clazzMapper.insert(clazz);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }

    /**
     * 基于pagehelper实现分页查询
     */
    @Override
    public PageResult<Clazz> page(EmpQueryParam empQueryParam) {
        //1.设置分页查询参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        //2.执行查询
        List<Clazz> list = clazzMapper.list(empQueryParam);

        //根据时间，来给clazz对象封装对应的状态··
/*      这里我们使用 SQL 方式计算，因为数据库计算通常比应用层快，减轻业务层负担
        LocalDate now = LocalDate.now();
        for (Clazz clazz : list) {
            LocalDate beginDate = clazz.getBeginDate();
            LocalDate endDate = clazz.getEndDate();
            if (now.isBefore(beginDate)) {
                clazz.setStatus("未开课");
            } else if (now.isAfter(endDate)) {
                clazz.setStatus("已结课");
            } else {
                clazz.setStatus("在读");
            }
        }
*/


        //3.解析查询结果，并封装
        Page<Clazz> p = (Page<Clazz>) list;


        return new PageResult<Clazz>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    @Override
    public Clazz queryById(Integer id) {
        Clazz clazz = clazzMapper.queryById(id);
        //根据时间，来给clazz对象封装对应的状态··
        LocalDate now = LocalDate.now();
        LocalDate beginDate = clazz.getBeginDate();
        LocalDate endDate = clazz.getEndDate();
        if (now.isBefore(beginDate)) {
            clazz.setStatus("未开课");
        } else if (now.isAfter(endDate)) {
            clazz.setStatus("已结课");
        } else {
            clazz.setStatus("在读");
        }

        return clazz;
    }

    @Override
    public void update(Clazz clazz) {
        clazzMapper.update(clazz);
    }
}
