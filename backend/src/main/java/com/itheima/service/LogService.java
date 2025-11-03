package com.itheima.service;


import com.itheima.pojo.LogQueryParam;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;

public interface LogService {
    PageResult<OperateLog> page(LogQueryParam logQueryParam);
}
