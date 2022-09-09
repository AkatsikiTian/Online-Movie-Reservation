package com.usst.myorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    //用户名
    private String username;
    //姓名
    private String nickname;

    //手机号
    private String phone;


    //性别 0 女 1 男
    private String sex;

    //密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    //头像
    private String avatar;


    //状态 0:禁用，1:正常
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
