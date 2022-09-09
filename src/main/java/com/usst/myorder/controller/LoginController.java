package com.usst.myorder.controller;
import com.usst.myorder.service.LoginService;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户
        Result login = loginService.login(loginParam);
        System.out.println(login.getData());
        return login;
    }
    @PostMapping("/login")
    public Result login1(@RequestBody LoginParam loginParam){
        //登录 验证用户
        Result login = loginService.login1(loginParam);
        System.out.println(login.getData());
        return login;
    }
}
