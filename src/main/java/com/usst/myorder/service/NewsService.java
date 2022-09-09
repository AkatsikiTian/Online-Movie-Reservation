package com.usst.myorder.service;

import com.usst.myorder.entity.News;
import com.usst.myorder.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface NewsService {
    int update(News news);

    int delete(List<Long> ids);

    int delete(Long id);

    News getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);

    Integer save(News news);

    Result savePicture(MultipartFile file) throws IOException;

    List<News> getall();
}
