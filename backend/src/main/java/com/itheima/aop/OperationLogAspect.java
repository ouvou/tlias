package com.itheima.aop;

import com.itheima.anno.Log;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 环绕通知
    @Around("@annotation(com.itheima.anno.Log)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 当前时间
        long endTime = System.currentTimeMillis();
        // 耗时
        long costTime = endTime - startTime;

        // 构建日志对象
        OperateLog oLog = new OperateLog();
        //OperateLog对象的id属性，会在数据库中自增设置，我们这里不设置
        oLog.setOperateEmpId(getCurrentUserId()); // 需要实现 getCurrentUserId 方法
        oLog.setOperateTime(LocalDateTime.now());//获取操作时间

        oLog.setClassName(joinPoint.getTarget().getClass().getName());//获取目标类名
        oLog.setMethodName(joinPoint.getSignature().getName());//获取目标方法名
        oLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));//获取目标方法参数
        oLog.setReturnValue(result.toString());//获取目标方法返回值
        oLog.setCostTime(costTime);//获取目标操作耗时

        // 打印日志
        log.info("记录日志操作: {}",oLog);

        // 插入日志
        operateLogMapper.insert(oLog);
        return result;
    }
    
    // 示例方法，获取当前用户ID
    private int getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}