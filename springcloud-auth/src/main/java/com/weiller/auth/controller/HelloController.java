package com.weiller.auth.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.weiller.auth.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.FluxOperator;

import java.util.*;

@RestController
public class HelloController {

    @Autowired
    IHelloService helloService;

    @Autowired
    DiscoveryClient discoveryClient;


    public Object helloDefault(){
        return "I am default hello";
    }

    @HystrixCommand( fallbackMethod = "helloDefault",
            threadPoolKey = "helloOneThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "30"),
                    @HystrixProperty(name = "maxQueueSize",value = "10")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "15000"),
                    @HystrixProperty(name = "metrics.rollingPercentile.numBuckets",value = "5"),


            })
    @RequestMapping(value = "/hello/get",method = RequestMethod.GET)
    public Object helloOne(){
        // 随机1/3概率休眠10秒钟
        int randomNum = new Random().nextInt(3);
        if(randomNum == 1){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return helloService.sayOnce();
    }

    @RequestMapping(value = "/hello/list",method = RequestMethod.GET)
    public Object helloMore(){
        return helloService.saySome();
    }

    @GetMapping("/provider/discovery")
    public Object discovery(){
        List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD-PRIVIDER-8081");

        List<Map> list = new ArrayList<>();
        for(ServiceInstance info : instances ){
            Map map = new HashMap();
            map.put("Host",info.getHost());
            map.put("ServiceId",info.getServiceId());
            map.put("Port",info.getPort());
            map.put("Uri",info.getUri());
            map.put("Metadata",info.getMetadata());
            list.add(map);
        }
        return FluxOperator.fromIterable(list);
    }
}
