package com.demo.lab.base;

import com.sun.net.httpserver.HttpExchange;

public class NotFoundHandler extends BaseHandler {

    @Override
    protected void process(HttpExchange httpExchange) throws Exception {
        writeError(httpExchange, ResponseFactory.routeOrMethodNotFoundError());
    }

}
