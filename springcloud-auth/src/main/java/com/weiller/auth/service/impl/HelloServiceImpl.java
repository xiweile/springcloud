package com.weiller.auth.service.impl;

import com.weiller.auth.service.IHelloService;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloServiceImpl implements IHelloService {

    @Value("${server.port}")
    private String port;

    @Value("${info.author}")
    private String author;

    @Override
    public Mono<Msg> sayOnce() {
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("hello world ,I am privider "+port +" author :" +author);
        return MonoOperator.just(msg);
    }

    @Override
    public Flux<Msg> saySome() {
        Msg msg1 = new Msg();
        msg1.setCode("200");
        msg1.setMsg("hello world 2018"+" author :" +author);
        Msg msg2 = new Msg();
        msg2.setCode("200");
        msg2.setMsg("hello world 2019"+" author :" +author);
        List<Msg> list = new ArrayList<>();
        list.add(msg1);
        list.add(msg2);
        return FluxOperator.fromIterable(list);
    }
}
