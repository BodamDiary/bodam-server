package com.ssafy.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:env.properties", ignoreResourceNotFound = true)
public class PropertyConfig {
}
