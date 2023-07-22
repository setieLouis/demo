package com.example.demo.client;

import feign.Headers;
import feign.RequestLine;

public interface Demo2api {

    @RequestLine("POST /ciao")
    @Headers("Content-Type: application/json")
    Response<String> ciao(Element element);


    @RequestLine("GET /mao")
    void mao();
}
