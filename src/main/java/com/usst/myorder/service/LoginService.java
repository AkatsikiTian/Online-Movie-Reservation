package com.usst.myorder.service;



import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    Result logout(String token);

    Result login1(LoginParam loginParam);


}
