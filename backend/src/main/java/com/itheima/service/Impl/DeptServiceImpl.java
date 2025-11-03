package com.itheima.service.Impl;

import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) throws Exception {
        // 1. 检查该部门下是否有员工
        Long employeeCount = empMapper.countByDeptId(id);

        // 2. 如果有员工，则抛出异常
        if (employeeCount > 0) {
            throw new Exception("对不起，当前部门下有"+employeeCount+"名员工，不能直接删除！");
        }

        // 3. 如果没有员工，则删除部门
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        //1. 补全基础属性 -createTime -updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用mapper接口插入数据
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {

        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        //1. 补全基础属性 -updateTime
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用mapper接口更新部门数据
        deptMapper.update(dept);
    }
}
