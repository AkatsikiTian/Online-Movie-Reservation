package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.Arrangement;
import org.apache.ibatis.annotations.Update;

public interface ArrangementMapper extends BaseMapper<Arrangement> {
    @Update("alter table order.arrangement AUTO_INCREMENT = 1")
    Integer updateautoid();
}
