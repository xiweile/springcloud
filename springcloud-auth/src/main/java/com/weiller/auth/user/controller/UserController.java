package com.weiller.auth.user.controller;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.api.auth.service.UserService;
import com.weiller.auth.user.entity.User;
import com.weiller.auth.user.service.IUserService;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController implements UserService{

    @Autowired
    private IUserService iUserService;

    @PutMapping("/user")
    public User create(@RequestBody User   user ) {
        return this.iUserService.createOrUpdate(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getList() {
        return this.iUserService.findAll();
    }

    public Mono<User> get(@PathVariable("id") Integer id ) {
        return this.iUserService.getById(id);
    }

    public Mono<Tuple2> delete(@PathVariable("id")Integer id){
        Tuple2<Boolean, String> tuple2 = Tuples.of(true, "删除成功！");
        return Mono.just(tuple2);
    }


    @RequestMapping({ "/user", "/me" })
    public Map<String,Object> user(Principal user){
        //Principal 被oauth2拦截封装 为OAuth2Authentication
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user",user.getName());
        //userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

    @RequestMapping("/userInfo")
    public Map<String,Object> userInfo(Principal principal){
        //在经OAuth2拦截后，是OAuth2Authentication
        OAuth2Authentication authentication = (OAuth2Authentication) principal;

        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user",authentication.getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(authentication.getUserAuthentication().getAuthorities()));
        return userInfo;
    }



    //@PostMapping("/login")
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


    //@PostMapping("/logout")
    public Object logout(){
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("用户退出");
        return msg;
    }

    public Msg isOnline(){
        Msg msg = new Msg();
        msg.setCode(MsgCode.SUCCESS.getCode());
        msg.setMsg("用户在线");
        return msg;
    }

}
