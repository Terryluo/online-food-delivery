package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid;
    private String secret; // secret key
    private String mchid; //merchant id
    private String mchSerialNo; // merchant API serial number
    private String privateKeyFilePath; //merchant private key
    private String apiV3Key;
    private String weChatPayCertFilePath;
    private String notifyUrl;
    private String refundNotifyUrl;

}
