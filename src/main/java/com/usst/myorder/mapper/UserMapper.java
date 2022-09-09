package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.User;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {
    @Update("alter table order.user AUTO_INCREMENT = 1")
    Integer updateautoid();
}
