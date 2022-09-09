package com.usst.myorder.vo;

import com.usst.myorder.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderVo {
    private String id;

    //订单号
    private String number;

    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
    private Integer status;


    private User user;

    //电影排班id
    private Long aid;

    private ArrangementVo arrangementVo;

    //下单时间
    private LocalDateTime orderTime;

    //电影id
    private Long filmid;

    //seat
    private String seat;


    //实收金额
    private Double price;


    //手机号
    private String phone;
}
