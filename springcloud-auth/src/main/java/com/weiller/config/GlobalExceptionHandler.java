package com.weiller.config;

import com.weiller.utils.model.Msg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseBody
    public Msg allExceptionHandler(HttpServletRequest request,
            Exception exception) throws Exception  
    {
        String message = exception.getMessage();
        System.out.println(message);
        return Msg.failure("500","服务器异常，请联系管理员！");
    }  

}