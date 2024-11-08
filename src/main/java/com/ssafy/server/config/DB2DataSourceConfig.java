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
@MapperScan(basePackages = "com.ssafy.server.model.daosc", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class DB2DataSourceConfig {

    @Bean
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("DB2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db2/*.xml"));
        factoryBean.setTypeAliasesPackage("com.ssafy.server.model.dto");
        return factoryBean.getObject();
    }

}
