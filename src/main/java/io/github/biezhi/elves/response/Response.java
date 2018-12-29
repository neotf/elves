package io.github.biezhi.elves.response;

import io.github.biezhi.elves.request.Request;
import lombok.Getter;

import java.io.InputStream;

/**
 * 响应对象
 *
 * @author biezhi
 * @date 2018/1/11
 */
public class Response {

    @Getter
    private Request request;
    private Body body;
    private int code;

    public Response(Request request, int code, InputStream inputStream) {
        this.request = request;
        this.code = code;
        this.body = new Body(inputStream, request.charset());
    }

    public Body body() {
        return body;
    }

    public int code() {
        return code;
    }

}
