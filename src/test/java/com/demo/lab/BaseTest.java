package com.demo.lab;

import com.demo.lab.base.BaseHandler;
import com.demo.lab.login.LoginHandler;
import com.demo.lab.user.UserHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.when;

public class BaseTest {

    protected InputStream getInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    protected String mockThenRequest(BaseHandler baseHandler, String method, String body) {
        return mockThenRequest(baseHandler, "http://localhost", "abc", method, body);
    }

    protected String mockThenRequest(BaseHandler baseHandler, String url, String token, String method, String body) {
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestBody()).thenReturn(getInputStream(body));
        URI uri = getUri(url);
        when(httpExchange.getRequestURI()).thenReturn(uri);

        Headers requestHeaders = new Headers();
        requestHeaders.set("Authorization", "Bearer " + token);
        ;

        when(httpExchange.getRequestHeaders()).thenReturn(requestHeaders);
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getResponseBody()).thenReturn(outputStream);

        baseHandler.handle(httpExchange);
        return outputStream.toString();
    }

    protected String getLoginToken() {
        mockThenRequest(new UserHandler(), TestConstant.POST, createUserBody());
        String tokenResponse = mockThenRequest(new LoginHandler(), TestConstant.POST, loginBody());

        int sepIndex = tokenResponse.indexOf(":");
        int start = tokenResponse.indexOf("\"", sepIndex);
        int end = tokenResponse.indexOf("\"", start + 1);

        return tokenResponse.substring(start + 1, end);
    }

    private URI getUri(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
            ;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            try {
                uri = new URI("http://localhost");
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
        return uri;
    }

    protected String createUserBody() {
        return "{\n" +
            "    \"username\": \"lzwjava\",\n" +
            "    \"password\": \"123\"\n" +
            "}";
    }

    protected String loginBody() {
        return "{\n" +
            "    \"username\": \"lzwjava\",\n" +
            "    \"password\": \"123\"\n" +
            "}";
    }
}
