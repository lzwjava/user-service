package com.demo.lab.base;

public class ResponseFactory {

    public static Response success() {
        return new Response(200, "", "OK");
    }

    private static String code(int index) {
        return String.format("LAB-USER-%d", index);
    }

    public static Response internalError(String message) {
        return new Response(500, code(0), message);
    }

    public static Response userExistError() {
        return new Response(500, code(1), "The user with this username already exists");
    }

    public static Response routeOrMethodNotFoundError() {
        return new Response(404, code(2), "route or method not found");
    }

    public static Response userNotFoundError() {
        return new Response(404, code(3), "user is not found");
    }

    public static Response userDeletionFailed() {
        return new Response(500, code(4), "user deletion failed");
    }

    public static Response roleExistError() {
        return new Response(500, code(5), "The role with this name already exists");
    }

    public static Response roleDeletionFailed() {
        return new Response(500, code(6), "role deletion failed");
    }

    public static Response userCreationFailed() {
        return new Response(500, code(7), "user deletion failed");
    }

    public static Response roleCreationFailed() {
        return new Response(500, code(8), "role deletion failed");
    }

    public static Response userRoleCreationFailed() {
        return new Response(500, code(8), "user role creation failed");
    }

    public static Response loginFailed() {
        return new Response(500, code(9), "login failed cause user with that username and password is not found");
    }

    public static Response tokenExpiredError() {
        return new Response(401, code(10), "token expired");
    }

    public static Response wrongTokenError() {
        return new Response(401, code(11), "token is wrong or not provided");
    }

    public static Response badRequestError(String key) {
        return new Response(408, code(12), "please provide correct header param or body param of name: " + key);
    }

}
