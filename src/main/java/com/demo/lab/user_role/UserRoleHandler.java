package com.demo.lab.user_role;

import com.demo.lab.base.BaseHandler;
import com.demo.lab.base.CreateResponse;
import com.demo.lab.base.ResponseFactory;
import com.demo.lab.role.Role;
import com.demo.lab.role.RoleRepository;
import com.demo.lab.user.User;
import com.demo.lab.utils.JSONUtils;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRoleHandler extends BaseHandler {

    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;

    public UserRoleHandler() {
        userRoleRepository = UserRoleRepository.getInstance();
        roleRepository = RoleRepository.getInstance();
    }

    protected void process(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals(POST)) {
            String body = readBody(httpExchange);

            Map<String, Object> params = JSONUtils.fromString(body);
            verifyParams(params, USER_ID, ROLE_ID);

            UserRole userRole = JSONUtils.mapToObject(params, UserRole.class);

            UserRole targetUserRole = userRoleRepository.getUserRole(userRole);
            if (targetUserRole != null) {
                writeSuccessResponse(httpExchange);
            } else {
                boolean ok = userRoleRepository.saveUserRole(userRole);
                if (ok) {
                    writeSuccessResponse(httpExchange, new CreateResponse(userRole.getId()));
                } else {
                    writeError(httpExchange, ResponseFactory.userRoleCreationFailed());
                }
            }
        } else if (method.equals(GET)) {
            User user = checkTokenAndGetUser(httpExchange);
            if (user == null) {
                writeError(httpExchange, ResponseFactory.wrongTokenError());
                return;
            }

            Map<String, String> params = getParams(httpExchange);
            String roleIdStr = params.get(ROLE_ID);
            if (roleIdStr != null) {
                int roleId = Integer.parseInt(roleIdStr);
                UserRole userRole = userRoleRepository.getUserRole(user.getId(), roleId);

                boolean check = userRole != null;
                writeSuccessResponse(httpExchange, new UserRoleCheckResponse(check));
            } else {
                List<UserRole> userRoles = userRoleRepository.getUserRoles(user.getId());
                List<Role> roles = userRoles.stream().map(userRole -> roleRepository.getEntityById(userRole.getRoleId())).collect(Collectors.toList());
                writeSuccessResponse(httpExchange, roles);
            }
        }
    }

}