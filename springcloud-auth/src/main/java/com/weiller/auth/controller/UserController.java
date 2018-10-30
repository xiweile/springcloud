package com.weiller.auth.controller;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.api.auth.service.UserService;
import com.weiller.auth.entity.User;
import com.weiller.auth.service.IUserService;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController implements UserService{

    @Autowired
    private IUserService iUserService;

    public Mono<User> create(@RequestBody User   user ) {
        return this.iUserService.createOrUpdate(user);
    }

    public Flux<User> getList() {
        return this.iUserService.findAll();
    }

    public Mono<User> get(@PathVariable("id") Integer id ) {
        return this.iUserService.getById(id);
    }

    public Mono<Tuple2> delete(@PathVariable("id")Integer id){
        Tuple2<Boolean, String> tuple2 = Tuples.of(true, "删除成功！");
        return Mono.just(tuple2);
    }

    @PostMapping("/user")
    public Map<String,Object> user(OAuth2Authentication user){
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user",user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

    @PostMapping("/login")
    public Object login(@RequestBody UserVo userVo){

        Msg msg = new Msg();
        userVo.setId(null);
        User  userMono = iUserService.getByUsernameAndPassword(userVo);
        if(userMono!= null){
            msg.setCode(MsgCode.SUCCESS.getCode());
            msg.setMsg("登陆成功");
            msg.setData(userMono);
        }else{
            msg.setCode(MsgCode.AUTH_LOGIN_ERROR.getCode());
            msg.setMsg(MsgCode.AUTH_LOGIN_ERROR.getDesc());
        }
        return msg;
    }


    @PostMapping("/logout")
    public Object logout(){
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("用户退出");
        return msg;
    }


    @PostMapping("/online")
    public Msg isOnline(){
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("用户在线");
        return msg;
    }

}
