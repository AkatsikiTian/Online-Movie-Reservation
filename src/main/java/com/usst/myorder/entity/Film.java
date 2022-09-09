package com.usst.myorder.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 菜品
 */
@Data
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value ="id", type = IdType.AUTO)
    private Long id;


    //电影名称
    private String name;


    //电影分类id
    private Long categoryId;

    //图片
    private String image;


    //描述信息
    private String description;


    //0 下架 1 起售
    private Integer status;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //购买个数
    private Integer count;

    //电影时长
    private Integer time;

    //电影上映时间
    private LocalDateTime uptime;

    //导演
    private String director;

    //主演
    private String actors;

    //语言
    private String language;
}
