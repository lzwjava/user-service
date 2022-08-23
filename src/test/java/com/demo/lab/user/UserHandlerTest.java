package com.demo.lab.user;

import com.demo.lab.BaseTest;
import com.demo.lab.TestConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest extends BaseTest {

    UserHandler userHandler;

    @BeforeEach
    void before() {
        userHandler = new UserHandler();
    }

    @Test
    void testCreate() {
        String s = mockThenRequest(userHandler, TestConstant.POST, createUserBody());
        assertFalse(s.isEmpty());
    }

    @Test
    void testDelete() {
        String s = mockThenRequest(userHandler, "http://localhost/users/1","abc",  TestConstant.DELETE, "");

        assertNotNull(s);
        assertTrue(s.contains("OK"));
    }

}
