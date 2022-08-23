package com.demo.lab.utils;

import com.demo.lab.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenUtilsTest {

    @Test
    void testEncryptPassword() {
        String encrypted = TokenUtils.encryptPassword("abc");
        assertEquals("ungWv48Bz+pBQUDeXa4iI7ADYaOWF3qctBD/YfIAFa0=", encrypted);
    }

    @Test
    void checkTokenExpired() {
        long tokenExpired = TokenUtils.tokenExpired();
        boolean expired = TokenUtils.checkTokenExpired(tokenExpired - 1);
        assertFalse(expired);

        expired = TokenUtils.checkTokenExpired(tokenExpired + 1000);
        assertTrue(expired);

        expired = TokenUtils.checkTokenExpired(System.currentTimeMillis());
        assertFalse(expired);
    }

    @Test
    void testGenerateToken() {
        User user = new User();
        user.setUsername("lzwjava");
        user.setPassword("123");

        String token = TokenUtils.generateToken(user);
        assertNotNull(token);
    }

}
