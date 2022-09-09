package com.usst.myorder.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private Integer admin;

    private String sex;

    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
