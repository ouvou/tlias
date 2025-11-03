package com.itheima.config;

import com.itheima.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置类
 */
@Configuration //本质@Component
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private DemoInterceptor demoInterceptor;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    /**
     * 需要在 addInterceptors 方法中通过 registry.addInterceptor()
     * 注册拦截器，并指定拦截路径，否则拦截器不会对请求起作用。
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)//添加拦截器
                .addPathPatterns("/**")//添加拦截路径 - /** 表示所有路径
                .excludePathPatterns("/login");//添加排除路径 - 登录接口不拦截

    }
}
