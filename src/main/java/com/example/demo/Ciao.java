package com.example.demo;


import com.example.demo.client.Demo2Cleint;
import com.example.demo.client.Demo2api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.List;

@RestController

@RequiredArgsConstructor
public class Ciao {

    private final Prova ma;
    private final TraxEnvsConf traxEnvsConf;
    private final Demo2Cleint demo2Cleint;

    @GetMapping("/ciao")
    public String ciao(){

        return demo2Cleint.doCall();
    }

    @GetMapping("/mao")
    public String mao(){

        return "ciao";
    }
}
