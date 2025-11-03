package com.itheima.service.Impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.Student;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    EmpMapper empMapper;
    @Autowired
    StudentMapper studentMapper;

    /**
     * 统计各个职位的员工人数
     */
    @Override
    public JobOption getEmpJobData() {
        //1.调用Mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();

        //2.组装结果，并返回
        //获取职位列表
        List<Object> jobList = list.stream().map(jobMap -> jobMap.get("pos")).toList();
        //获取数据列表
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList,dataList);
    }

    /**
     * 统计各个性别的员工人数
     */
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        //调用Mapper接口，获取统计数据
        return empMapper.countEmpGenderData();
    }

    /**
     * 统计各个学员的学历信息
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    /**
     * 统计每一个班级的人数
     */
    @Override
    public ClazzOption getStudentCountData() {
        //1.调用Mapper接口，获取统计数据
        List<Map<String, Object>> list = studentMapper.getStudentCountData();

        //2.组装结果，并返回
        //获取班级列表
        List<Object> clazzList = list.stream().map(clazzMap -> clazzMap.get("clazz")).toList();
        //获取人数列表
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new ClazzOption(clazzList,dataList);
    }
}
