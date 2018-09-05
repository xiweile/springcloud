package com.weiller.config.hello.service.impl;

import com.weiller.config.hello.entity.Msg;
import com.weiller.config.hello.service.HelloService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloServiceImpl implements HelloService{

    @Override
    public Mono<Msg> sayOnce() {
        Msg msg = new Msg();
        msg.setCode("200");
        msg.setMsg("hello world ,I am privider 8001");
        Mono<Msg> msgMono = MonoOperator.just(msg);
        return msgMono;
    }

    @Override
    public Flux<Msg> saySome() {
        Msg msg1 = new Msg();
        msg1.setCode("200");
        msg1.setMsg("hello world 2018");
        Msg msg2 = new Msg();
        msg2.setCode("200");
        msg2.setMsg("hello world 2019");
        List<Msg> list = new ArrayList<>();
        list.add(msg1);
        list.add(msg2);
        Flux<Msg> msgFlux = FluxOperator.fromIterable(list);
        return msgFlux;
    }
}
