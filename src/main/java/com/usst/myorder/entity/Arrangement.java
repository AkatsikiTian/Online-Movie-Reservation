package com.usst.myorder.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 安排表
 */
@Data
public class Arrangement implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;


    //电影id
    private Long filmid;


    //放映厅
    private Long houseid;


    //座位个数
    private Integer seatsize;

    //单价
    private Double price;

    //放映时间
    private LocalDateTime uptime;


    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
