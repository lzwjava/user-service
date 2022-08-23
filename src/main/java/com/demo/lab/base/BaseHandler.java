package com.demo.lab.base;

import com.demo.lab.user.User;
import com.demo.lab.user.UserRepository;
import com.demo.lab.utils.JSONUtils;
import com.demo.lab.utils.TokenUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseHandler implements HttpHandler {

    protected static final String USER_ID = "userId";
    protected static final String ROLE_ID = "roleId";
    protected static final String DELETE = "DELETE";
    protected static final String POST = "POST";
    protected static final String GET = "GET";
    protected static final String AUTHORIZATION = "Authorization";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    protected static final String ROLE_NAME = "name";
    protected static final String ID = "id";

    protected UserRepository userRepository;

    public BaseHandler() {
        userRepository = UserRepository.getInstance();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            synchronized (this) {
                process(httpExchange);
            }
        } catch (HandleException handleException) {
            handleException.printStackTrace();
            writeError(httpExchange, handleException.getResponse());
        } catch (Exception e) {
            e.printStackTrace();
            writeError(httpExchange, ResponseFactory.internalError(e.getMessage()));
        }
    }

    protected abstract void process(HttpExchange httpExchange) throws Exception;

    protected String readBody(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        int b;
        StringBuilder sb = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            sb.append((char) b);
        }
        br.close();
        inputStreamReader.close();
        return sb.toString();
    }

    protected void verifyParams(Map<String, Object> params, String... keys) {
        String str = Arrays.stream(keys).filter(key -> Objects.isNull(params.get(key)))
            .collect(Collectors.joining(","));
        if (!str.isEmpty()) {
            throw new HandleException(ResponseFactory.badRequestError(str));
        }
    }

    protected void writeSuccessResponse(HttpExchange httpExchange) {
        writeObject(httpExchange, 200, ResponseFactory.success());
    }

    protected void writeSuccessResponse(HttpExchange httpExchange, Object object) {
        writeObject(httpExchange, 200, object);
    }

    protected void writeObject(HttpExchange httpExchange, int code, Object object) {
        try {
            String string = JSONUtils.toJsonString(object);
            writeString(httpExchange, code, string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeError(HttpExchange httpExchange, Response response) {
        writeObject(httpExchange, response.getStatus(), response);
    }

    protected void writeString(HttpExchange httpExchange, int code, String string) {
        try {
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-type", "application/json");
            httpExchange.sendResponseHeaders(code, string.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(string.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getIdInUrlPath(HttpExchange httpExchange) {
        URI requestURI = httpExchange.getRequestURI();
        String path = requestURI.getPath();
        int last = path.lastIndexOf('/');
        String substring = path.substring(last + 1);
        try {
            return Integer.valueOf(substring);
        } catch (NumberFormatException numberFormatException) {
            throw new HandleException(ResponseFactory.badRequestError(ID));
        }
    }

    protected Map<String, String> getParams(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        return queryToMap(query);
    }

    protected Map<String, String> queryToMap(String query) {
        if (query == null) {
            return new HashMap<>();
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    protected String getToken(HttpExchange httpExchange) {
        String authorization = httpExchange.getRequestHeaders().getFirst(AUTHORIZATION);
        if (authorization == null) {
            return null;
        }
        String[] strs = authorization.split(" ");
        if (strs.length != 2) {
            return null;
        }
        return strs[1];
    }

    protected User checkTokenAndGetUser(HttpExchange httpExchange) {
        String token = getToken(httpExchange);
        if (token == null) {
            return null;
        }

        User user = userRepository.getUserByToken(token);

        if (user == null) {
            writeError(httpExchange, ResponseFactory.wrongTokenError());
            return null;
        }

        boolean expired = TokenUtils.checkTokenExpired(user.getTokenExpired());
        if (expired) {
            writeError(httpExchange, ResponseFactory.tokenExpiredError());
        }
        return user;
    }
}
