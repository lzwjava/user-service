package com.demo.lab.utils;

import com.demo.lab.user.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class TokenUtils {

    public static final int TOKEN_EXPIRED_SECONDS = 60 * 60 * 2;

    private static String randomString(int len) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, len);
    }

    private static String sha256AndBase64(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encryptPassword(String password) {
        return sha256AndBase64(password);
    }

    public static String generateToken(User user) {
        String str = user.getUsername() + user.getPassword() + System.currentTimeMillis() + randomString(4);
        return sha256AndBase64(str);
    }

    public static long tokenExpired() {
        return System.currentTimeMillis() + 1000 * TOKEN_EXPIRED_SECONDS;
    }

    public static boolean checkTokenExpired(long tokenExpired) {
        long period = tokenExpired - System.currentTimeMillis();
        boolean notExpired = period >= 0 && period < 1000 * TOKEN_EXPIRED_SECONDS;
        return !notExpired;
    }

}
