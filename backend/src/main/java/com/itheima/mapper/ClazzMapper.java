package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {
    /**
     * 新增班级
     * 注释说明了将 clazz 对象的属性值赋值给 clazz 表中对应字段的操作
     */
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) value (#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void insert(Clazz clazz);

    /**
     * 查询所有班级信息
     */
    @Select("select id, name, room, begin_date, end_date, master_id, subject, create_time, update_time from clazz")
    List<Clazz> findAll();

    /**
     * 带条件分页查询班级信息
     */
    List<Clazz> list(EmpQueryParam empQueryParam);

    /**
     * 根据id删除班级
     * @param id
     */
    @Delete("delete from clazz where id = #{id};")
    void deleteById(Integer id);

    /**
     * 根据id查询班级的全部信息
     * @param id
     * @return
     */
    @Select("select c.id, c.name, c.room, c.begin_date, c.end_date, c.master_id, c.subject, c.create_time, c.update_time,e.name as master_name from clazz c join emp e on c.master_id = e.id where c.id = #{id}")
    Clazz queryById(Integer id);

    /**
     * 修改班级数据
     * @param clazz
     */
    @Update("update clazz set name=#{name},room=#{room},begin_date=#{beginDate},end_date=#{endDate},master_id = #{masterId},subject=#{subject} where id = #{id}")
    void update(Clazz clazz);
}
