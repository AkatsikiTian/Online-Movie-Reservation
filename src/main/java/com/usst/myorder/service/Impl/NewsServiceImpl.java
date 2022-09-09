package com.usst.myorder.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Film;
import com.usst.myorder.entity.News;
import com.usst.myorder.mapper.NewsMapper;
import com.usst.myorder.service.NewsService;
import com.usst.myorder.vo.FilmVo;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;
    @Override
    public int update(News news) {
        return newsMapper.updateById(news);
    }

    @Override
    public int delete(List<Long> ids) {
        return newsMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return newsMapper.deleteById(id);
    }

    @Override
    public News getById(Long id) {
        return newsMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<News> newsPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        newsPage = newsMapper.selectPage(newsPage, null);
        List<News> list = newsPage.getRecords();
        map.put("total", newsMapper.selectCount(null));
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<News> newsPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(select,input);
        newsPage = newsMapper.selectPage(newsPage, queryWrapper);
        List<News> list = newsPage.getRecords();
        map.put("total",newsMapper.selectCount(queryWrapper));
        map.put("data",list);
        return map;
    }

    @Override
    public Integer save(News news) {
        newsMapper.updateautoid();
        news.setCreateTime(LocalDateTime.now());
        return newsMapper.insert(news);
    }

    @Override
    public Result savePicture(MultipartFile file) throws IOException {
        String originalFilenamePath = file.getOriginalFilename();
        String sysPath = ResourceUtils.getURL("classpath:").getPath();
        int index = sysPath.indexOf("target");
        sysPath = sysPath.substring(0,index)+"backgroundmanagement/public/" +"static/news";
        sysPath = sysPath.replace('/', '\\').substring(1,sysPath.length());
        String realPath = "/static/news/"+originalFilenamePath;
        File dest = new File(sysPath,originalFilenamePath);
        file.transferTo(dest);
        return Result.succ(realPath);
    }

    @Override
    public List<News> getall() {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        queryWrapper.last("limit 4");
        List<News> list = newsMapper.selectList(queryWrapper);
        return list;
    }
}
