package com.itheima.service;

import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计各个职位的员工人数
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计各个性别的员工人数
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计各个学员的学历信息
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计每一个班级的人数
     */
    ClazzOption getStudentCountData();
}
