package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.News;
import org.apache.ibatis.annotations.Update;

public interface NewsMapper extends BaseMapper<News> {
    @Update("alter table order.news AUTO_INCREMENT = 1")
    Integer updateautoid();
}
