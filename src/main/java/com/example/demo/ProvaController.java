package com.example.demo;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("api")
public class ProvaController {

     @GetMapping("/ciao")
     public Hello sayHello(){

         return Hello.builder().message("hello").build();
     }



     @Data
     @Builder
     public static class Hello{
         private final String message;
     }
}
