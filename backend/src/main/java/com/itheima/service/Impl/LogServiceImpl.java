package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.LogQueryParam;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> page(LogQueryParam logQueryParam) {
        //1.设置分页查询参数
        PageHelper.startPage(logQueryParam.getPage(),logQueryParam.getPageSize());
        //2.执行查询
        List<OperateLog> logList = operateLogMapper.list(logQueryParam);
        //3.解析查询结果，并封装
        Page<OperateLog> page = (Page<OperateLog>) logList;
        return new PageResult<OperateLog>(page.getPages(),page.getResult());
    }
}
