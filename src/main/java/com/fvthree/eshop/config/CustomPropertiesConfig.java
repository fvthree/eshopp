package com.fvthree.eshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("eshop.app")
@Data
public class CustomPropertiesConfig {
	
	private String jwtSecret;

    private int jwtExpirationMs;
    
    private int jwtRefreshExpirationMs;

}
