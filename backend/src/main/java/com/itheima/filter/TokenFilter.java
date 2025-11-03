package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@Slf4j
@WebFilter("/*")//拦截所有请求
@Order(1)//设置过滤器的执行顺序，数字越小，优先级越高 - 来自 spring 的注解
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转，以便能够使用HTTP特有的方法和属性。
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1．获取到请求路径
        //URI 是资源的名称或标识
        //URL 是资源的位置和访问方式
        String path = request.getRequestURI(); // /login

        //2．判断是否是登录请求，如果路径中包含/login，说明是登录操作，放行
        if (path.contains("login")){
            log.info("登录操作,放行");
            filterChain.doFilter(request,response);
            return;
        }

        //3．获取请求头中的token
        String token = request.getHeader("token");

        //4. 判断token是否存在，如果不存在，说明用户没有登录，返回错误信息（响应401状态码）
        if (token==null || token.isEmpty()){
            log.info("令牌为空，响应状态码401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //5．如果token存在，校验令牌，如果校验失败 -> 返回错误信息（响应401状态码）
        try {
            //claims 是 JWT 令牌中存储的内容，存储格式是Map
            Claims claims = JwtUtils.parseJWT(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
        } catch (Exception e) {
            log.info("令牌校验失败，响应状态码401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //6．校验通过，放行
        log.info("令牌校验通过，放行");
        filterChain.doFilter(request,response);

        //7. 删除ThreadLoacl中的数据,释放资源
        CurrentHolder.remove();


    }
}
