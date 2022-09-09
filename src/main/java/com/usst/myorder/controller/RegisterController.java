package com.usst.myorder.controller;

import cn.hutool.crypto.SecureUtil;
import com.usst.myorder.entity.User;
import com.usst.myorder.service.UserService;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    UserService userService;
    private static final String slat = "hdglxt!@#";
    @PostMapping
    public Result register(@RequestBody Register register){
        User user = new User();
        user.setNickname(register.getNickname());
        user.setPassword(SecureUtil.md5(register.getPassword()+slat));
        user.setUsername(register.getUsername());
        user.setPhone(register.getPhone());
        user.setAvatar("/static/avatar/默认.jpg");
        return Result.succ(userService.save(user));
    }
}
