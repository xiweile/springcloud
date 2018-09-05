package com.weiller.config.hello.controller;


import com.weiller.config.hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.FluxOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello/get",method = RequestMethod.GET)
    public Object helloOne(){
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
