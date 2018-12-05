package com.weiller.auth.service;

import com.weiller.identity.utils.model.Msg;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHelloService {

    Mono<Msg> sayOnce();

    Flux<Msg> saySome();
}
