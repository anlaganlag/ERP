package com.tadpole.cloud.externalSystem.modular.lingxing.okhttp;

import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpRequest;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpResponse;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpResponseImpl;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Slf4j
public class HttpExecutor {

    private static final HttpExecutor INSTANCE = new HttpExecutor();

    private HttpExecutor() {
    }

    public static HttpExecutor create() {
        return INSTANCE;
    }

    public <R> HttpResponse execute(HttpRequest<R> request) throws Exception {
        log.info("Executing Request: 请求方式[{}]，请求路径[{}]，Query参数[{}]，请求参数[{}]", request.getMethod(), request.getEndpoint() + request.getPath(), request.getQueryParams(), request.getJson());
        HttpCommand<R> command = HttpCommand.create(request);
        return invokeRequest(command);
    }

    private <R> HttpResponse invokeRequest(HttpCommand<R> command) throws Exception {
        Response response = command.execute();
        if (command.getRetries() == 0 && response.code() != 200) {
            return invokeRequest(command.incrementRetriesAndReturn());
        }
        return HttpResponseImpl.wrap(response);
    }

}
