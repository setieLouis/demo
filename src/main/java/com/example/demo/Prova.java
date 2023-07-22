package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "ciao")
public class Prova {
    private Map<String,String> map;

    public Prova(Map<String,String> map){
        this.map = map;
    }
}
