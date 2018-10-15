package com.weiller.auth.controller;

import com.alibaba.fastjson.JSON;
import com.weiller.api.auth.service.UserService;
import com.weiller.api.comm.IdentitySessionClient;
import com.weiller.auth.entity.User;
import com.weiller.auth.service.IUserService;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController implements UserService{

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IdentitySessionClient identitySessionClient;


    @PostMapping("/user")
    public Mono<User> create(@RequestBody User   user ) {
        return this.iUserService.createOrUpdate(user);
    }

    @GetMapping("/user/all")
    public Flux<User> getList() {
        return this.iUserService.findAll();
    }

    @GetMapping("/user/{id}")
    public Mono<User> get(@PathVariable("id") Integer id ) {
        return this.iUserService.getById(id);
    }

    @DeleteMapping("/user/{id}")
    public Mono<Tuple2> delete(@PathVariable("id")Integer id){
        Tuple2<Boolean, String> tuple2 = Tuples.of(true, "删除成功！");
        return Mono.just(tuple2);
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user,
                        HttpServletRequest request){

        Msg msg = new Msg();
        user.setId(null);
        User  userMono = iUserService.getByUsernameAndPassword(user);
        if(userMono!= null){
            msg.setCode(MsgCode.SUCCESS.getCode());
            msg.setMsg("登陆成功");
            msg.setData(userMono);

            String sessionid = null;
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (Cookie cookie: cookies){
                    String name = cookie.getName();
                    if(IdentitySessionClient.SESSION_ID_KEY.equalsIgnoreCase(name)){// 获取sessionid值
                        sessionid = cookie.getValue();
                    }
                }
            }

            if(sessionid != null){
                identitySessionClient.setSession(sessionid, JSON.toJSONString(userMono));
            }else{
                msg.setCode(MsgCode.ERROR.getCode());
                msg.setMsg("登录异常");
            }
        }else{
            msg.setCode(MsgCode.AUTH_LOGIN_ERROR.getCode());
            msg.setMsg(MsgCode.AUTH_LOGIN_ERROR.getDesc());
        }
        return msg;
    }

    @PostMapping("/online")
    public Msg isOnline(){
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("在线");
        return msg;
    }

}
