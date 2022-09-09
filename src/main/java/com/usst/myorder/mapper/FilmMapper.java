package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.Film;
import org.apache.ibatis.annotations.Update;

public interface FilmMapper extends BaseMapper<Film> {
    @Update("alter table order.film AUTO_INCREMENT = 1")
    Integer updateautoid();
}
