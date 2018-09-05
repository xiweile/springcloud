package com.weiller.config.hello.service;

import com.weiller.config.hello.entity.Msg;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HelloService {

    Mono<Msg> sayOnce();

    Flux<Msg> saySome();
}
