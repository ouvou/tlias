package com.itheima.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
//@ConfigurationProperties&#xFF1A;&#x6279;&#x91CF;&#x5C06;&#x591A;&#x4E2A;&#x5C5E;&#x6027;&#x6CE8;&#x5165;&#x5230;bean&#x5BF9;&#x8C61;&#x4E2D;
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
