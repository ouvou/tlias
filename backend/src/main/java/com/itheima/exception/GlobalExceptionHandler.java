package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
/*@RestControllerAdvice 是一个组合注解，
相当于 @ControllerAdvice 和 @ResponseBody 的结合体。
它用于定义全局异常处理器类，可以统一处理整个应用程序中所有控制器抛出的异常，
并自动将返回结果序列化为 JSON 格式响应给客户端。
* */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错了！",e);
        return Result.error("程序出错了！请联系管理员");
    }

    /**
    * .@ExceptionHandler 用于标识处理特定异常的方法。
    * 当控制器抛出对应异常时，Spring 会自动调用该方法处理异常。（调用方法的顺序是从子类异常到父类异常）
    * DuplicateKeyException 是 Spring 框架中的数据访问异常
      表示违反了数据库的唯一性约束
    * */
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错了！",e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2]+"已经存在！！！");
    }

    /**
     * 处理score参数为 null 或者 字符串 类型时的错误
     * MethodArgumentTypeMismatchException 是Spring MVC中参数类型不匹配时抛出的异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("程序出错了！",e);
      /* 关于 "score".equals(e.getName()) 的说明
        参数名称匹配：这段代码用于检查异常中涉及的参数名称是否为 "score"
        精准异常处理：通过判断参数名来提供针对性的错误提示
      */
        log.info("异常参数名: "+e.getName());
        if ("score".equals(e.getName())) {
            // 检查是否是"null"字符串导致的转换失败
            if (e.getValue() != null && "null".equals(e.getValue().toString())) {
                return Result.error("违纪分数不能为空或纯空格！");
            }
            return Result.error("违纪分数格式不正确！");
        }
        //参数格式不正确的同一处理
        return Result.error("参数格式不正确！");
    }

    /**
     * DataIntegrityViolationException 是 Spring 框架中的数据完整性约束异常，通常在以下情况触发：
     * 数据长度超限等数据库约束问题
     * 违反数据库唯一约束（如重复键值）
     * 外键约束冲突
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("程序出错了！",e);
        return Result.error("学员违纪：总扣分最大值为255！！！");
    }

    /**
     *  NoResourceFoundException 是Spring Web MVC 框架中的异常类
     *  触发场景: 当请求的资源（URL路径）在服务器中找不到对应处理器时抛出
     *  HTTP状态码: 通常对应 404 Not Found 状态
     *  通常在以下情况触发：
     *      URL路径错误: 用户访问了不存在的接口路径
     *      请求方法不匹配: URL正确但HTTP方法（GET、POST等）不支持
     *      路径变量缺失: RESTful接口缺少必要的路径参数
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result handleNoResourceFound(NoResourceFoundException e) {
        // 获取请求URL
        String requestUrl = e.getResourcePath();

        // 检查是否是学生违纪处理接口
        if (requestUrl != null && requestUrl.contains("/violation/")) {
            return Result.error("请求路径不正确，请检查违纪分数参数格式");
        }
        log.error("程序出错了！",e);
        return Result.error("请求路径不正确，请检查参数格式");
    }
}
