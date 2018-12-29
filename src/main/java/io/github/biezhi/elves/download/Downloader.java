package io.github.biezhi.elves.download;

import io.github.biezhi.elves.request.Request;
import io.github.biezhi.elves.response.Response;
import io.github.biezhi.elves.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 下载器线程
 *
 * @author biezhi
 * @date 2018/1/11
 */
@Slf4j
public class Downloader implements Runnable {

    private final Scheduler scheduler;
    private final Request request;

    public Downloader(Scheduler scheduler, Request request) {
        this.scheduler = scheduler;
        this.request = request;
    }

    @Override
    public void run() {
        log.debug("[{}] 开始请求", request.getUrl());
        io.github.biezhi.request.Request httpReq = null;
        if ("get".equalsIgnoreCase(request.method())) {
            httpReq = io.github.biezhi.request.Request.get(request.getUrl());
        }
        if ("post".equalsIgnoreCase(request.method())) {
            httpReq = io.github.biezhi.request.Request.post(request.getUrl());
        }

        io.github.biezhi.request.Request req = httpReq.contentType(request.contentType()).headers(request.getHeaders())
                .connectTimeout(request.getSpider().getConfig().timeout())
                .readTimeout(request.getSpider().getConfig().timeout());

        int code = req.code();
        InputStream result = req.stream();

        log.debug("[{}] 下载完毕", request.getUrl());
        Response response = new Response(request, code, result);
        scheduler.addResponse(response);
    }

}
