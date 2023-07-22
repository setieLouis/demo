package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class OwnLogger extends feign.Logger {


    @Override
    protected void logRequest(String configKey, Level logLevel, feign.Request request) {

        String protocolVersion = resolveProtocolVersion(request.protocolVersion());

        RequestLog.RequestLogBuilder req = RequestLog.builder()
            .protocol(protocolVersion)
            .url(request.url())
            .method(request.httpMethod().name())
            .header(request.headers());

        if (nonNull(req.body))
            req.body(new String(request.body()));

        log(configKey, req.build());
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {

        ResponseLog.ResponseLogBuilder responseLog = ResponseLog.builder()
                .header(response.headers())
                .status(response.status())
                .reason(response.reason());
        if (response.body() != null && !(response.status() == 204 || response.status() == 205)) {

            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
             responseLog.body(new String(bodyData));
             response = response.toBuilder().body(bodyData).build();
        }


        log(configKey, responseLog.build());
        return  response;
    }



    @Override
    protected void log(String configKey, String format, Object... args) {

        if (isNull(args))
            return;

        log.info(String.format(methodTag(configKey) + format, args));

    }
    private void log(String configKey, ResponseLog responseLog) {
        log.info(String.format(methodTag(configKey) + " response: " + responseLog.toString()));
    }

    private void log(String configKey, RequestLog request) {

        log.info(String.format(methodTag(configKey) + " request: " + request.toString()));
    }

    @Builder
    @Data
    public static class RequestLog implements Serializable {
        private String protocol;
        private String method;
        private String url;
        private Map header;
        private String body;

        public String toString(){

            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (JsonProcessingException e) {

            }
            return super.toString();
        }

    }

    @Builder
    @Data
    public static class ResponseLog implements Serializable {
        private int status;
        private String reason;
        private Map header;
        private String body;

        public String toString(){

            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (JsonProcessingException e) {

            }
            return super.toString();
        }

    }
}



