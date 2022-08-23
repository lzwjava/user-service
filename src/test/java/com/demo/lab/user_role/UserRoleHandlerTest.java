package com.demo.lab.user_role;

import com.demo.lab.BaseTest;
import com.demo.lab.TestConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleHandlerTest extends BaseTest {

    UserRoleHandler userRoleHandler;

    @BeforeEach
    void before() {
        userRoleHandler = new UserRoleHandler();
    }

    @Test
    void testCreate() {
        String s = mockThenRequest(userRoleHandler, TestConstant.POST, createUserRoleBody());
        assertTrue(s.contains("id"));
    }

    @Test
    void testCheckRoleFailed() {
        String s = mockThenRequest(userRoleHandler, "http://localhost?roleId=1", "abc", TestConstant.GET,
            createUserRoleBody());
        assertTrue(s.contains("401"));
    }

    @Test
    void testCheckRoleSuccess() {
        String loginToken = getLoginToken();
        assertNotNull(loginToken);

        String s = mockThenRequest(userRoleHandler, "http://localhost?roleId=1", loginToken, TestConstant.GET, "");
        assertTrue(s.contains("false"));
    }

    @Test
    void testGetUserRoleList(){
        String loginToken = getLoginToken();
        assertNotNull(loginToken);

        String s = mockThenRequest(userRoleHandler, "http://localhost/users/roles", loginToken, TestConstant.GET, "");
        assertEquals("[]", s);;
    }

    private String createUserRoleBody() {
        return "{\n" +
            "    \"userId\": 1,\n" +
            "    \"roleId\": 1\n" +
            "}";
    }

}
