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

@Configuration
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
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        Connector connector = redirectConnector();
        logger.info("Adding HTTP connector on port: {}", connector.getPort());
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);

        return connector;
    }
}