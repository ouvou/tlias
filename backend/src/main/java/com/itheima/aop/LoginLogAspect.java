package com.itheima.aop;

import com.itheima.mapper.EmpLogMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpLoginLog;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LoginLogAspect {
    @Autowired
    EmpLogMapper empLogMapper;

    @Around("execution(public * com.itheima.controller.LoginController.login(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 当前时间
        long endTime = System.currentTimeMillis();
        // 耗时
        long costTime = endTime - startTime;

        //获取原始方法行参,并将其封装进Emp对象
         Emp emp = (Emp) joinPoint.getArgs()[0];



        // 构建日志对象
        EmpLoginLog eLog = new EmpLoginLog();
        // 需要实现 getCurrentUserId 方法

        eLog.setUsername(emp.getUsername());//设置用户名
        eLog.setPassword(emp.getPassword());//设置用户密码
        eLog.setLoginTime(LocalDateTime.now());//设置登录时间
        eLog.setCostTime(costTime);//设置耗时

        // 默认登录失败
        short isSuccess = 0;
        String jwt = null;

        // 处理登录结果
        if (result instanceof Result) {
            Result resultObj = (Result) result;
            // 判断登录是否成功
            isSuccess = (resultObj.getCode() == 1) ? (short) 1 : 0;

            // 如果登录成功且数据是LoginInfo类型，获取JWT令牌
            if (isSuccess == 1 && resultObj.getData() instanceof LoginInfo) {
                LoginInfo loginInfo = (LoginInfo) resultObj.getData();
                jwt = loginInfo.getToken();
                log.info("员工登录成功，JWT令牌：{}", jwt);
            } else if (isSuccess == 1) {
                log.info("员工登录成功但数据不是LoginInfo类型");
            } else {
                log.info("员工登录失败");
            }
        } else {
            log.info("员工登录失败或返回类型不匹配");
        }

        // 设置登录结果和JWT令牌
        eLog.setIsSuccess(isSuccess);
        eLog.setJwt(jwt);

        // 统一记录日志并插入数据库
        log.info("员工登录{}，{}", isSuccess == 1 ? "成功" : "失败", eLog);
        empLogMapper.insertLoginLog(eLog);

        return result;//返回结果
    }
}
