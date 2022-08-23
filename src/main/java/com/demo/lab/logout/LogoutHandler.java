package com.demo.lab.logout;

import com.demo.lab.base.BaseHandler;
import com.demo.lab.base.ResponseFactory;
import com.demo.lab.user.User;
import com.sun.net.httpserver.HttpExchange;

public class LogoutHandler extends BaseHandler {

    @Override
    protected void process(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();
        if (method.equals(POST)) {
            User user = checkTokenAndGetUser(httpExchange);
            if (user == null) {
                writeError(httpExchange, ResponseFactory.wrongTokenError());
                return;
            }

            user.setToken(null);
            user.setTokenExpired(0);
            writeSuccessResponse(httpExchange);
        } else {
            writeError(httpExchange, ResponseFactory.routeOrMethodNotFoundError());
        }
    }

}
