package com.itheima.controller;

import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import com.itheima.service.Impl.ReportServiceImpl;
import com.itheima.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    private EmpService empService;

    /**
     * 统计各个职位的员工人数
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计各个职位的员工人数");
        JobOption empJobData = reportService.getEmpJobData();
        return Result.success(empJobData);
    }

    /**
     * 统计各个性别的员工人数
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计各个性别的员工人数");
        List<Map<String, Object>> list = reportService.getEmpGenderData();
        return Result.success(list);
    }

    /**
     * 统计各个学员的学历信息
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData() {
        log.info("统计各个学员的学历信息");
        List<Map<String, Object>> list = reportService.getStudentDegreeData();
        return Result.success(list);
    }

    /**
     * 统计每一个班级的人数
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        log.info("统计每一个班级的人数");
        ClazzOption clazzData = reportService.getStudentCountData();
        return Result.success(clazzData);
    }
}
