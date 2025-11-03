package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    EmpExprMapper empExprMapper;
    @Autowired
    EmpLogService empLogService;

    /**
     * 原始分页查询
     * @param page 页码
     * @param pageSize 每页记录数
     * @return
     */
/*  @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //获取起始索引
        Integer start = (page-1)*pageSize;

        //1.调用mapper接口，查询总记录数
        Long total = empMapper.count();
        //2.调用mapper接口，获取结果列表
        List<Emp> list = empMapper.list(start, pageSize);
        //3.封装分页结果
        return new PageResult<Emp>(total,list);
    }*/

    /**
     * 基于pagehelper插件实现分页查询
     * 注意事项：
     * 1.定义的SQL语句结尾不能加上分号;
     * 因为：pagehelper会将我们写的查询语句，改写成两条
     * 第一条是通过聚合函数获取总记录数
     * 第二条是在查询语句后面加上limit关键字来改写成分页查询
     * 而后者是直接在我们原有的sql语句中加上的，如果原有sql语句有；号就会报错
     * 2.PageHelper只会对紧跟其后的第一个查询语句进行分页处理
     * 因为：
     * 这样设计是为了避免分页参数 “乱跑”，确保只有你想分页的那个查询才会被处理。
     */
   /* @Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        //1.设置分页查询参数
        PageHelper.startPage(page,pageSize);
        //2.执行查询
        List<Emp> empList = empMapper.list(name,gender,begin,end);
        //3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }*/
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页查询参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        //2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
        //3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    /**
     * 新增员工
     *
     * @param emp
     */
    //@Transactional：会在方法运行之前，开启事务，运行完毕后，根据运行的结果，来提交或回滚事务。
    /*
    rollbackFor属性用于控制出现何种异常类型，回滚事务

    * */
    @Transactional(rollbackFor = {Exception.class})//事务管理 -> 默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp emp) throws Exception {
        try {
            //补全基础属性 -createTime -updateTime
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //1. 保存员工基本信息
            empMapper.insert(emp);


            //2. 保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合, 为empId赋值
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工:" + emp);
            empLogService.insertLog(empLog);
        }
    }

    /**
     * 查询全部员工（班主任）信息
     */
    @Override
    public List<Emp> findAll() {
        return empMapper.findAll();
    }

    /**
     * 根据员工ID来批量删除员工
     */
    //设置出现任何异常，事务都会回滚
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteByIds(List<Integer> ids) {
        //1.根据id来批量删除员工基本信息
        empMapper.deleteByIds(ids);

        //2.根据id来批量删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    /**
     * 根据ID查询员工信息及员工的工作经历信息
     */
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    /**
     * 根据ID修改员工信息及员工的工作经历信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1.根据ID修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());

        empMapper.updateById(emp);

        //2.根据ID修改员工的工作经历信息
        //2.1 先根据员工id删除原有工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));


        //2.2 再添加这个员工的新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!exprList.isEmpty()){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1.调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        //2.判断员工是否存在，如果存在，组装登录成功信息
        if (e!=null){
            log.info("登陆成功，员工信息：{}",e);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id",e.getId());
            dataMap.put("username",e.getUsername());

            String token = JwtUtils.generateJwt(dataMap);
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),token);
        }
        //3.不存在，直接返回null
        return null;
    }

    @Override
    public void updatePassword(UserPassword userPassword) {
        // 1. 查询用户信息
        Emp emp = empMapper.getById(userPassword.getId());
        if (emp == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 验证旧密码是否正确
        if (!emp.getPassword().equals(userPassword.getOldPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        // 3. 更新新密码
        empMapper.updatePassword(userPassword.getId(), userPassword.getNewPassword());
    }
}
