package com.cs.personer.client1.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 熔断器默认设置
 */
@DefaultProperties(defaultFallback = "defaultFallback")
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
    @HystrixCommand
    public String getFeign(@RequestParam String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name + port;
    }

    /**
     * 指定超时时间4s,覆盖默认配置.
     */
    @HystrixCommand(fallbackMethod = "fallback", commandProperties =
            {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "4000")})
    @GetMapping("/getHystrix")
    public String hystrix(@RequestParam String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name + port;
    }

    private String defaultFallback() {
        return "默认提示:系统繁忙";
    }

    private String fallback(String name) {
        return "系统繁忙";
    }
}
