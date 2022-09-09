package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.House;
import org.apache.ibatis.annotations.Update;

public interface HouseMapper extends BaseMapper<House> {
    @Update("alter table order.house AUTO_INCREMENT = 1")
    Integer updateautoid();
}
