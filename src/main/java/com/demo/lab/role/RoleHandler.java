package com.demo.lab.role;

import com.demo.lab.utils.JSONUtils;
import com.demo.lab.base.BaseHandler;
import com.demo.lab.base.CreateResponse;
import com.demo.lab.base.ResponseFactory;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class RoleHandler extends BaseHandler {

    private RoleRepository roleRepository;

    public RoleHandler() {
        roleRepository = roleRepository.getInstance();
    }

    protected void process(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals(POST)) {
            String body = readBody(httpExchange);
            Map<String, Object> bodyMap = JSONUtils.fromString(body);

            verifyParams(bodyMap, ROLE_NAME);

            Role role = JSONUtils.mapToObject(bodyMap, Role.class);

            Role targetRole = roleRepository.getRoleByName(role.getName());
            if (targetRole != null) {
                writeError(httpExchange, ResponseFactory.roleExistError());
            } else {
                boolean ok = roleRepository.saveRole(role);
                if (ok) {
                    writeSuccessResponse(httpExchange, new CreateResponse(role.getId()));
                } else {
                    writeError(httpExchange, ResponseFactory.roleCreationFailed());
                }
            }
        } else if (method.equals(DELETE)) {
            int id = getIdInUrlPath(httpExchange);
            boolean ok = roleRepository.deleteRole(id);
            if (ok) {
                writeSuccessResponse(httpExchange);
            } else {
                writeError(httpExchange, ResponseFactory.roleDeletionFailed());
            }
        } else {
            writeError(httpExchange, ResponseFactory.routeOrMethodNotFoundError());
        }
    }
}