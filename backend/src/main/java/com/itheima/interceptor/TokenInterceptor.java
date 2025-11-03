package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // 通过设置放行路径，省略这里的步骤，直接通过

        /* //1．获取到请求路径
        //URI 是资源的名称或标识
        //URL 是资源的位置和访问方式
        String path = request.getRequestURI(); // /login

        //2．判断是否是登录请求，如果路径中包含/login，说明是登录操作，放行
        //if (path.contains("login")){
        //    log.info("登录操作,放行");
        //    return true;
        //}

        //3．获取请求头中的token
        String token = request.getHeader("token");

        //4。判断token是否存在，如果不存在，说明用户没有登录，返回错误信息（响应401状态码）
        if (token==null || token.isEmpty()){
            log.info("令牌为空，响应状态码401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //5．如果token存在，校验令牌，如果校验失败 -> 返回错误信息（响应401状态码）
        try {
            Claims claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            log.info("令牌校验失败，响应状态码401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //6．校验通过，放行
        log.info("令牌校验通过，放行");
        return true;
        */
        return true;
    }
}
