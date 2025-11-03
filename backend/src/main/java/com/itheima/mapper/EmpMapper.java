package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {

    //--------------------------------原始分页查询实现----------------------------
    /**
     * 查询总记录数
     *//*
    @Select("select count(*) from emp e left outer join dept d on e.dept_id = d.id")
    public Long count();*/

    /**
     * 分页查询
     *//*
    @Select("select e.*,d.name as deptName from emp e left outer join dept d on e.dept_id = d.id " +
            "order by e.update_time desc limit #{start},#{pageSize}")
    public List<Emp> list(Integer start,Integer pageSize);*/

    //================================基于pagehelper插件实现分页查询=========================
    //不需要考虑任何与分页查询相关的操作
//  @Select("select e.*,d.name as deptName from emp e left outer join dept d on e.dept_id = d.id order by e.update_time desc")
    //public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

    /*
    * 保存员工
    * useGeneratedKeys = true ：表示启用 MyBatis 的主键回写功能,我们会使用数据库生成的主键
    * keyProperty：插入数据后会自动将生成的主键值设置到实体类的对应属性中（由 keyProperty 指定）
    * */
    @Options(useGeneratedKeys = true,keyProperty = "id")//获取到生成的主键 -- Mybatis的主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    /**
     * 查询全部员工（班主任）信息
     */
    @Select("select id, username, name, gender, image, job, entry_date, salary, dept_id, create_time, update_time from emp where job = 1")
    List<Emp> findAll();

    /**
     * 根据id来批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);


    /**
     * 根据ID查询员工信息及员工的工作经历信息
     */
    Emp getById(Integer id);

    /**
     * 根据ID修改员工信息及员工的工作经历信息
     */
    void updateById(Emp emp);

    /**
     * 统计各个职位的员工人数
     */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();

    /**
     * 统计各个性别的员工人数
     */
    @MapKey("clazz")
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select id, username, name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);

    /**
     * 查询部门人数
     */
    @Select("select count(*) from dept d left outer join emp e on d.id = e.dept_id where d.id = #{id} and e.dept_id is not null")
    Long countByDeptId(Integer id);

    /**
     * 更新员工密码
     * @param id 员工ID
     * @param password 新密码
     */
    @Update("UPDATE emp SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);
}
