package com.usst.myorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;

    //订单号
    private String number;

    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
    private Integer status;


    //下单用户id
    private Long userId;

    //电影排班id
    private Long aid;


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
