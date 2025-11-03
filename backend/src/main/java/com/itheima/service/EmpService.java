package com.itheima.service;

import com.itheima.pojo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface EmpService {
    /**
     * 分页查询
     * @param empQueryParam 参数对象
     * @return
     */
    //PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 添加员工
     * @param emp 
     */
    void save(Emp emp) throws Exception;

    List<Emp> findAll();


    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工信息及员工的工作经历信息
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工
     */
    void update(Emp emp);


    /**
     * 登录操作
     */
    LoginInfo login(Emp emp);

    /**
     * 修改员工密码
     * @param userPassword 密码信息
     */
    void updatePassword(UserPassword userPassword);
}
