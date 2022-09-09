package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.Category;
import org.apache.ibatis.annotations.Update;

public interface CategoryMapper extends BaseMapper<Category> {
    @Update("alter table order.category AUTO_INCREMENT = 1")
    Integer updateautoid();
}
