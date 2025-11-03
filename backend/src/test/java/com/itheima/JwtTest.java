package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /**
     * 生成JWT令牌 - Jwts.builder()
     */
    @Test
    public void testGenerateJWT(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");
        String jwt = Jwts.builder()//创建JWT构建器
                .signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==")//指定加密签名算法 和 密钥
                .setClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600))//设置令牌有效期
                .compact();//创建JWT令牌
        System.out.println("JWT令牌："+jwt);
    }


    /**
     * 解析JWT令牌 - Jwts.parser()
     */
    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzU5MDY5MjU1LCJ1c2VybmFtZSI6ImFkbWluIn0.20nsRDa6vMM5SAK6_hlTN6qgc6NzXNNuuPTCaTh7kLo";
        Claims claims = Jwts.parser()//创建JWT解析器
                .setSigningKey("aXRoZWltYQ==")//指定密钥
                .parseClaimsJws(token)//解析JWT令牌
                .getBody();//获取自定义信息
        System.out.println(claims);

    }

}
