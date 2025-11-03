package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T>{
    private long total;
    private List<T> rows;
}
