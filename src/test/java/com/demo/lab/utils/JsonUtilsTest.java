package com.demo.lab.utils;

import com.demo.lab.user.User;
import com.demo.lab.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilsTest extends BaseTest {

    @Test
    void testFromString() {
        Map<String, Object> map = JSONUtils.fromString(createUserBody());
        assertEquals("lzwjava", map.get("username"));
        assertEquals("123", map.get("password"));
    }

    @Test
    void testToJsonString() {
        User user = new User();
        user.setUsername("lzwjava");
        user.setPassword("123");
        String jsonString = JSONUtils.toJsonString(user);
        assertEquals("{\"id\": 0,\"username\": \"lzwjava\",\"password\": \"123\",\"token\": \"\",\"tokenExpired\": 0}", jsonString);
    }

    @Test
    void testMapToObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "lzwjava");
        map.put("password", "abc");

        User user = JSONUtils.mapToObject(map, User.class);

        assertEquals("lzwjava", user.getUsername());
        assertEquals("abc", user.getPassword());
    }

}
