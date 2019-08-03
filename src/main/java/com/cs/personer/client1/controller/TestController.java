package com.cs.personer.client1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
public class TestController {
    @Value("${server.port}")
    private String port;

    @GetMapping
    public String test() {
        return "test1:" + port;
    }

    @GetMapping("/getFeign")
    public String getFeign(@RequestParam String name) {
        return name + port;
    }
}
