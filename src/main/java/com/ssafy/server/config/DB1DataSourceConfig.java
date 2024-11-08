package com.ssafy.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ssafy.server.model.dao", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class DB1DataSourceConfig {

    @Bean
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("DB1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml"));
        factoryBean.setTypeAliasesPackage("com.ssafy.server.model.dto");

        org.apache.ibatis.session.Configuration myBatisConfig = new org.apache.ibatis.session.Configuration();
        myBatisConfig.setMapUnderscoreToCamelCase(true); // 언더스코어를 카멜 케이스로 매핑
        factoryBean.setConfiguration(myBatisConfig);

        return factoryBean.getObject();
    }

}