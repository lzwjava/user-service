package com.demo.lab.login;

import com.demo.lab.BaseTest;
import com.demo.lab.TestConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginHandlerTest extends BaseTest {

    LoginHandler loginHandler;

    @BeforeEach
    void before() {
        loginHandler = new LoginHandler();
    }

    @Test
    void testLoginFailed() {
        String s = mockThenRequest(loginHandler, TestConstant.POST, loginBody());
        assertNotNull(s);
    }

}
