package com.demo.lab.logout;

import com.demo.lab.BaseTest;
import com.demo.lab.TestConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoutHandlerTest extends BaseTest {

    LogoutHandler logoutHandler;

    @BeforeEach
    void before() {
        logoutHandler = new LogoutHandler();
    }

    @Test
    void testLogoutFailed() {
        String s = mockThenRequest(logoutHandler, TestConstant.POST, logoutBody());
        assertTrue(s.contains("status"));
        assertTrue(s.contains("token is wrong or not provided"));
    }

    @Test
    void testLogoutSuccess() {
        String loginToken = getLoginToken();
        assertNotNull(loginToken);

        String s = mockThenRequest(logoutHandler, "http://localhost", loginToken, TestConstant.POST,
            logoutBody());

        assertTrue(s.contains("200"));
    }

    private String logoutBody() {
        return "{}";
    }

}
