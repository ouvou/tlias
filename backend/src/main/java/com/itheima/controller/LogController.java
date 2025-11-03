package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/log")
@Slf4j
@RestController
public class LogController {
    @Autowired
    LogService logService;
    /**
     * 该接口用于日志信息的分页查询
     */
    @GetMapping("/page")
    public Result page(LogQueryParam logQueryParam){
        log.info("分页查询：{}",logQueryParam);
        PageResult<OperateLog> pageResult = logService.page(logQueryParam);
        return Result.success(pageResult);
    }
}
