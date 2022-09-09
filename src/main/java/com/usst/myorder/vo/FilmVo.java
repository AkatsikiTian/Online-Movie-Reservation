package com.usst.myorder.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmVo {
    private String id;

    //电影名称
    private String name;

    //电影分类
    private String category;

    //电影分类id
    private String categoryId;


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
