package com.demo.lab.user;

import com.demo.lab.utils.JSONUtils;
import com.demo.lab.base.BaseHandler;
import com.demo.lab.base.CreateResponse;
import com.demo.lab.base.ResponseFactory;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class UserHandler extends BaseHandler {

    private UserRepository userRepository;

    public UserHandler() {
        userRepository = UserRepository.getInstance();
    }

    public void process(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals(POST)) {
            String body = readBody(httpExchange);
            Map<String, Object> params = JSONUtils.fromString(body);
            verifyParams(params, USERNAME, PASSWORD);

            User user = JSONUtils.mapToObject(params, User.class);

            User targetUser = userRepository.getUserByUsername(user.getUsername());
            if (targetUser != null) {
                writeError(httpExchange, ResponseFactory.userExistError());
            } else {
                boolean ok = userRepository.saveUser(user);
                if (ok) {
                    writeSuccessResponse(httpExchange, new CreateResponse(user.getId()));
                } else {
                    writeError(httpExchange, ResponseFactory.userCreationFailed());
                }
            }
        } else if (method.equals(DELETE)) {
            int id = getIdInUrlPath(httpExchange);
            User targetUser = userRepository.getUserById(id);
            if (targetUser == null) {
                writeError(httpExchange, ResponseFactory.userNotFoundError());
            } else {
                boolean ok = userRepository.deleteUser(id);
                if (ok) {
                    writeSuccessResponse(httpExchange);
                } else {
                    writeError(httpExchange, ResponseFactory.userDeletionFailed());
                }
            }
        } else {
            writeError(httpExchange, ResponseFactory.routeOrMethodNotFoundError());
        }
    }

}