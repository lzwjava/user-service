package com.demo.lab.login;

import com.demo.lab.utils.JSONUtils;
import com.demo.lab.utils.TokenUtils;
import com.demo.lab.base.BaseHandler;
import com.demo.lab.base.ResponseFactory;
import com.demo.lab.user.User;
import com.demo.lab.user.UserRepository;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class LoginHandler extends BaseHandler {

    private UserRepository userRepository;

    public LoginHandler() {
        userRepository = UserRepository.getInstance();
    }

    @Override
    protected void process(HttpExchange httpExchange) throws Exception {
        String method = httpExchange.getRequestMethod();
        if (method.equals(POST)) {
            String body = readBody(httpExchange);
            Map<String, Object> params = JSONUtils.fromString(body);

            verifyParams(params, USERNAME, PASSWORD);

            User user = JSONUtils.mapToObject(params, User.class);

            User targetUser = userRepository.getUser(user);
            if (targetUser == null) {
                writeError(httpExchange, ResponseFactory.loginFailed());
            } else {
                String token = TokenUtils.generateToken(user);
                long tokenExpired = TokenUtils.tokenExpired();

                targetUser.setToken(token);
                targetUser.setTokenExpired(tokenExpired);

                LoginResponse loginResponse = new LoginResponse(token, TokenUtils.TOKEN_EXPIRED_SECONDS);

                writeSuccessResponse(httpExchange, loginResponse);
            }
        } else {
            writeError(httpExchange, ResponseFactory.routeOrMethodNotFoundError());
        }
    }

}
