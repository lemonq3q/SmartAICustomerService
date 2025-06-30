package org.example.xiaomiai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession
@MapperScan("org.example.xiaomiai.mapper")
@SpringBootApplication
//@EnableCaching
public class XiaomiAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomiAiApplication.class, args);
    }

}
