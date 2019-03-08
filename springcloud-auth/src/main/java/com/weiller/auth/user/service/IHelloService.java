package com.weiller.auth.user.service;

import com.weiller.utils.model.Msg;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHelloService {

    Mono<Msg> sayOnce();

    Flux<Msg> saySome();
}
