package com.usst.myorder.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArrangementVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //电影id
    private Long filmid;

    //电影名称
    private String filmName;

    //放映厅
    private Long houseid;

    //放映厅名称
    private String housename;

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
