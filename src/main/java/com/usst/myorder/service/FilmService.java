package com.usst.myorder.service;

import com.usst.myorder.entity.Film;
import com.usst.myorder.vo.FilmVo;
import com.usst.myorder.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FilmService {
    int update(Film film);

    int delete(List<Long> ids);

    int delete(Long id);

    FilmVo getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);

    Integer save(Film film);

    Result savePicture(MultipartFile file) throws IOException;

    List<Film> searchFilm(String search);

    List<FilmVo> getall();

    Map<String,Object> hotFilm(Integer currentPage, Integer pageSize);

    Map<String,Object> getFilm(Integer currentPage, Integer pageSize,Integer categoryId);

    Map<String,Object> hotFilmlist();
}
