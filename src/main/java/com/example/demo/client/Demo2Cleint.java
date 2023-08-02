package com.example.demo.client;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class Demo2Cleint {


    public String doCall(){

        Demo2api demo2Api = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logLevel(Logger.Level.FULL)
                //.logger(new OwnLogger())
                .target(Demo2api.class, "http://localhost:9002");
        demo2Api.mao();
        Response<String> response = demo2Api.ciao(Element.builder().nome("ciao").build());

        return response.getResponse();
    }
}
