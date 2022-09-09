package com.usst.myorder.vo.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class Register implements Serializable {
    private String username;
    private String nickname;
    private String password;
    private String phone;
}
