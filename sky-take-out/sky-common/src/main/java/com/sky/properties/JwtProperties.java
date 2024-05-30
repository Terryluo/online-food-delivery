package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * employee generate jwt
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * customer generate jwt
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
