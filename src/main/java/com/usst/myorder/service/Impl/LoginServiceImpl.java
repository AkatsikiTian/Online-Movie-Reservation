package com.usst.myorder.service.Impl;


import com.alibaba.fastjson.JSON;
import com.usst.myorder.entity.Employee;
import com.usst.myorder.entity.User;
import com.usst.myorder.service.EmployeeService;
import com.usst.myorder.service.LoginService;
import com.usst.myorder.service.UserService;
import com.usst.myorder.util.JWTUtils;
import com.usst.myorder.vo.ErrorCode;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String slat = "hdglxt!@#";

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getUsername();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        Employee employee = employeeService.findUser(account,password);
        if (employee == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        if (employee.getStatus()==1){
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg());
        }
        String token = JWTUtils.createToken(employee.getId());
        redisTemplate.opsForValue().set("AdminTOKEN_"+token, JSON.toJSONString(employee),1, TimeUnit.DAYS);
        return Result.succ(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("AdminTOKEN_"+token);
        return Result.succ(null);
    }

    @Override
    public Result login1(LoginParam loginParam) {
        String account = loginParam.getUsername();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        User user = userService.findUser(account,password);
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        if (user.getStatus()==1){
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg());
        }
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("AdminTOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.succ(token);
    }




}
