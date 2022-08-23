package com.demo.lab;

import com.demo.lab.base.NotFoundHandler;
import com.demo.lab.login.LoginHandler;
import com.demo.lab.logout.LogoutHandler;
import com.demo.lab.role.RoleHandler;
import com.demo.lab.user.UserHandler;
import com.demo.lab.user_role.UserRoleHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users/roles", new UserRoleHandler());
        server.createContext("/users", new UserHandler());
        server.createContext("/roles", new RoleHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/", new NotFoundHandler());
        server.setExecutor(null);
        server.start();
    }

}