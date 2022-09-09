package com.usst.myorder.controller;

import cn.hutool.crypto.SecureUtil;

import com.usst.myorder.entity.User;
import com.usst.myorder.service.UserService;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.PasswordParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public Result save(@Validated @RequestBody User user){
        String result;
        System.out.println(user);
        if (user.getId()!=null){
            result = userService.update(user);
        }else{
            user.setPassword(SecureUtil.md5(user.getPassword()+"hdglxt!@#"));
            user.setAvatar("/static/avatar/默认.jpg");
            result= String.valueOf(userService.save(user));
        }
        return Result.succ(result);
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(userService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(userService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(userService.findbyinput(currentPage,pageSize,select,input));
    }

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }

    @PostMapping("changepassword")
    public Result changepassword(@RequestHeader("Authorization") String token, @RequestBody PasswordParam passwordParam){
        return userService.changePassword(token,passwordParam) ;
    }

    @PostMapping("savePicture")
    private Result savePicture(@RequestParam MultipartFile file) throws IOException {
        return userService.savePicture(file);
    }
}

