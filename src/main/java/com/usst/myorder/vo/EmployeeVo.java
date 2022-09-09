package com.usst.myorder.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeVo {

    private String id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

}
