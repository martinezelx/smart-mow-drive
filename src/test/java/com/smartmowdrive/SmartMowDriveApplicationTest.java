package com.smartmowdrive;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class SmartMowDriveApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        assert(applicationContext != null);
    }
}