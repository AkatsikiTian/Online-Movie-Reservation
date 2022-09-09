package com.usst.myorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.usst.myorder.entity.Film;
import com.usst.myorder.mapper.FilmMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ThreadService {

        //期望此操作在线程池 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(FilmMapper filmMapper, Film film) {

        int viewCounts = film.getCount();
        Film filmUpdate = new Film();
        filmUpdate.setCount(viewCounts +1);
        filmUpdate.setUpdateTime(film.getUpdateTime());
        QueryWrapper<Film> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("id",film.getId());
        //设置一个 为了在多线程的环境下 线程安全
        updateWrapper.eq("count",viewCounts);
        filmMapper.update(filmUpdate,updateWrapper);
    }
}
