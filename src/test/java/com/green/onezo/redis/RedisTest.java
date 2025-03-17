package com.green.onezo.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void before(){
        redisService.setValue(KEY, VALUE, DURATION);


    }

    @AfterEach
    void after(){
        redisService.delete(KEY);
    }

    @Test
    @DisplayName("redis에 데이터를 저장 ")
    void saveData() throws Exception {

        String findValue = redisService.getValue(KEY);

        assertEquals(VALUE ,findValue);
        System.out.println(VALUE);
    }

    @Test
    @DisplayName("redis를 데이터 수정 ")
    void updateData(){

        String updateValue = "updateValue";
        redisService.setValue(KEY, updateValue, DURATION);

        String findValue = redisService.getValue(KEY);

        assertEquals(updateValue, findValue);
        assertEquals(VALUE, findValue);

    }




}
