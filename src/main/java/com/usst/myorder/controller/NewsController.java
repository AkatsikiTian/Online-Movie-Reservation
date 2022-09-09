package com.usst.myorder.controller;

import com.usst.myorder.cache.Cache;
import com.usst.myorder.entity.Film;
import com.usst.myorder.entity.News;
import com.usst.myorder.service.FilmService;
import com.usst.myorder.service.NewsService;
import com.usst.myorder.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    NewsService newsService;
    @PostMapping
    public Result save(@Validated @RequestBody News news){
        int result;
        if (news.getId()!=null){
            result = newsService.update(news);
        }else{
            result= newsService.save(news);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        int delete = newsService.delete(ids);
        Result result = Result.succ(delete);
        return result;
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        return Result.succ(newsService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(newsService.getById(id));
    }
    @Cache(expire=5*60*1000,name = "getNews")
    @GetMapping("load")
    public Result getall(){
        return Result.succ(newsService.getall());
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(newsService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(newsService.findbyinput(currentPage,pageSize,select,input));
    }

}
