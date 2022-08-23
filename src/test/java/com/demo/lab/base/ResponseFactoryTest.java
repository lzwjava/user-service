package com.demo.lab.base;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseFactoryTest {

    @Test
    public void testSuccess() {
        Response success = ResponseFactory.success();
        assertEquals(200, success.getStatus());
    }

    @Test
    public void testError() {
        Response userCreationFailed = ResponseFactory.userCreationFailed();
        assertEquals(500, userCreationFailed.getStatus());
        assertEquals("LAB-USER-7", userCreationFailed.getError());
        assertEquals("user deletion failed", userCreationFailed.getMessage());
    }

}
