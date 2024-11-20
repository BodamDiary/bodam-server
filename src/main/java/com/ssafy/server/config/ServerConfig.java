package com.ssafy.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    @Value("${server.http.port:80}")
    private int httpPort;

    @Value("${server.port:443}")
    private int httpsPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        logger.info("Configuring server with HTTP port: {} and HTTPS port: {}", httpPort, httpsPort);

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                logger.info("Configuring security constraints for HTTPS redirect");

                // HTTPS 리다이렉션을 위한 제약 조건 설정
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // HTTPS 포트 설정
        tomcat.setPort(httpsPort);

        // HTTP 커넥터 설정
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);

        // 추가 커넥터 설정
        connector.setAllowTrace(false);
        connector.setEnableLookups(false);
        connector.setMaxPostSize(10485760); // 10MB
        connector.setURIEncoding("UTF-8");

        logger.info("Adding HTTP connector on port: {}", connector.getPort());
        tomcat.addAdditionalTomcatConnectors(connector);

        return tomcat;
    }
}