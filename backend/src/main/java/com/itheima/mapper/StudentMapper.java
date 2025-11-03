package com.itheima.mapper;

import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    /**
     * 分页查询
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 根据id批量删除学员
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    @Insert("insert into student(name,no,gender,phone,id_card,is_college,address,degree,graduation_date,clazz_id, create_time, update_time)" +
            "values(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void insert(Student student);

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    @Select("select * from student where id =#{id}")
    Student selectById(Integer id);

    /**
     * 根据ID更新学生信息
     * @param student
     */
    void updateById(Student student);

    /**
     * 统计各个学员的学历信息
     */
    @MapKey("name")
    List<Map<String, Object>> countStudentDegreeData();

    /**
     * 统计每一个班级的人数
     */
    @MapKey("clazz")
    List<Map<String, Object>> getStudentCountData();
}
