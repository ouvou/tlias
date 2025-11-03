package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据id来批量删除员工工作经历信息
     */
    void deleteByEmpIds(List<Integer> empIds);
}
