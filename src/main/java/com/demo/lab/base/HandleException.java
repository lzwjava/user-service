package com.demo.lab.base;

public class HandleException extends RuntimeException {

    private final Response response;

    public HandleException(String message) {
        super(message);
        this.response = ResponseFactory.internalError(message);
    }

    public HandleException(Response response) {
        super(response.getMessage());
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

}
