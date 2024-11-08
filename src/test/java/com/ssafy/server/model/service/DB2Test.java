package com.ssafy.server.model.service;

import com.ssafy.server.BodamServerApplication;
import com.ssafy.server.model.daosc.SaltMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
public class DB2Test {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BodamServerApplication.class);

    @Autowired
    SaltMapper saltMapper;

    @Test
    @DisplayName("DB2 빈 체크")
    void test(){
        // given

        // when
        Object bean = ac.getBean("DB1DataSource");
        System.out.println(bean);

        // then
    }

    @Test
    @DisplayName("DB2 getSalt 체크")
    void saltCheck(){
        // given
        int userId = 10000;

        // when
        String salt = saltMapper.selectSalt(userId);

        // then
        Assertions.assertThat(salt).isEqualTo("hi");
    }
}
