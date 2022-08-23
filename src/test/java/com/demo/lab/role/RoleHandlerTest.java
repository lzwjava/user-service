package com.demo.lab.role;

import com.demo.lab.BaseTest;
import com.demo.lab.TestConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleHandlerTest extends BaseTest {

    RoleHandler roleHandler;

    @BeforeEach
    void before() {
        roleHandler = new RoleHandler();
    }

    @Test
    void testCreate() {
        String s = mockThenRequest(roleHandler, TestConstant.POST, createRoleBody());
        assertTrue(s.contains("id"));
    }

    @Test
    void testDelete() {
        String s = mockThenRequest(roleHandler,"http://localhost:8080/roles/1",
            "abc", TestConstant.DELETE, createRoleBody());
        assertNotNull(s);
    }

    private String createRoleBody() {
        return "{\n" +
            "    \"name\": \"admin\"\n" +
            "}";
    }

}
