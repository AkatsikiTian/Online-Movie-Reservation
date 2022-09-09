package com.usst.myorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class News implements Serializable {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    private String pict;
    private String title;
    private LocalDateTime createTime;
}
