package com.itheima.mapper;

import com.itheima.pojo.EmpLog;
import com.itheima.pojo.EmpLoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface EmpLogMapper {

    //插入员工操作日志
    @Insert("insert into emp_log (operate_time, info) values (#{operateTime}, #{info})")
    public void insert(EmpLog empLog);

    //插入员工登录日志
    @Mapper
    public void insertLoginLog(EmpLoginLog empLoginLog);

}
