package com.usst.myorder.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Category;
import com.usst.myorder.entity.Film;
import com.usst.myorder.mapper.CategoryMapper;
import com.usst.myorder.mapper.FilmMapper;
import com.usst.myorder.service.FilmService;
import com.usst.myorder.service.ThreadService;
import com.usst.myorder.vo.FilmVo;
import com.usst.myorder.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmMapper filmMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    private ThreadService threadService;

    @Override
    public int update(Film film) {
        film.setUpdateTime(LocalDateTime.now());
        return filmMapper.updateById(film);
    }

    @Override
    public int delete(List<Long> ids) {

        return filmMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return filmMapper.deleteById(id);
    }

    @Override
    public FilmVo getById(Long id) {
        Film film = filmMapper.selectById(id);
        FilmVo filmvo = copy(film);
        threadService.updateArticleViewCount(filmMapper,film);
        return filmvo;
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Film> filmPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        filmPage = filmMapper.selectPage(filmPage, null);
        List<Film> list = filmPage.getRecords();
        List<FilmVo> filmVos = copyList(list);
        map.put("total", filmMapper.selectCount(null));
        map.put("data", filmVos);
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<Film> filmPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(select,input);
        filmPage = filmMapper.selectPage(filmPage, queryWrapper);
        List<Film> list = filmPage.getRecords();
        List<FilmVo> sysUserVos = copyList(list);
        map.put("total",filmMapper.selectCount(queryWrapper));
        map.put("data",sysUserVos);
        return map;
    }

    @Override
    public Integer save(Film film) {
        filmMapper.updateautoid();
        film.setCreateTime(LocalDateTime.now());
        film.setUpdateTime(LocalDateTime.now());
        return filmMapper.insert(film);
    }

    @Override
    public Result savePicture(MultipartFile file) throws IOException {
        String originalFilenamePath = file.getOriginalFilename();
        String sysPath = ResourceUtils.getURL("classpath:").getPath();
        int index = sysPath.indexOf("target");
        sysPath = sysPath.substring(0,index)+"backgroundmanagement/public/" +"static/img";
        sysPath = sysPath.replace('/', '\\').substring(1,sysPath.length());
        String realPath = "/static/img/"+originalFilenamePath;
        File dest = new File(sysPath,originalFilenamePath);
        file.transferTo(dest);
        return Result.succ(realPath);
    }

    @Override
    public List<Film> searchFilm(String search) {
        LambdaQueryWrapper<Film> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Film::getCount);
        lambdaQueryWrapper.eq(Film::getStatus,1);
        if (!search.equals("**all")){
            lambdaQueryWrapper.like(Film::getName,search);
        }
        lambdaQueryWrapper.last("limit 5");
        List<Film> films = filmMapper.selectList(lambdaQueryWrapper);
        return films;
    }

    @Override
    public List<FilmVo> getall() {
        QueryWrapper<Film> queryWrapper = new QueryWrapper<Film>();
        queryWrapper.eq("status",1);
        List<Film> films = filmMapper.selectList(queryWrapper);
        List<FilmVo> filmVos = copyList(films);
        return filmVos;
    }

    @Override
    public Map<String, Object> hotFilm(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Film> filmPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Film> qw = new QueryWrapper<>();
        qw.eq("status",1);
        qw.orderByDesc("count","uptime");
        filmPage = filmMapper.selectPage(filmPage, qw);
        List<Film> list = filmPage.getRecords();
        List<FilmVo> filmVos = copyList(list);
        map.put("total", filmMapper.selectCount(qw));
        map.put("data", filmVos);
        return map;
    }

    @Override
    public Map<String, Object> getFilm(Integer currentPage, Integer pageSize, Integer categoryId) {
        Map<String, Object> map = new HashMap<>();
        IPage<Film> filmPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Film> qw = new QueryWrapper<>();
        qw.eq("status",1);
        if (categoryId!=-1){
            qw.eq("category_id",categoryId);
        }
        qw.orderByDesc("uptime");
        filmPage = filmMapper.selectPage(filmPage, qw);
        List<Film> list = filmPage.getRecords();
        List<FilmVo> filmVos = copyList(list);
        map.put("total", filmMapper.selectCount(qw));
        map.put("data", filmVos);
        return map;
    }

    @Override
    public Map<String, Object> hotFilmlist() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Film> qw = new QueryWrapper<>();
        qw.orderByDesc("count");
        qw.last("limit 5");
        List<Film> list = filmMapper.selectList(qw);
        List<FilmVo> filmVos = copyList(list);
        List<String> name = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (FilmVo f : filmVos){
            name.add(f.getName());
            count.add(f.getCount());
        }
        map.put("xAxis", name);
        map.put("data", count);
        return map;
    }


    private FilmVo copy(Film film){
        FilmVo filmVo = new FilmVo();
        filmVo.setId(String.valueOf(film.getId()));
        Category category = categoryMapper.selectById(film.getCategoryId());
        filmVo.setCategoryId(String.valueOf(film.getCategoryId()));
        filmVo.setCategory(category.getName());
        BeanUtils.copyProperties(film, filmVo);
        return filmVo;
    }

    private List<FilmVo> copyList(List<Film> films){
        ArrayList<FilmVo> filmVoArrayList = new ArrayList<>();
        for (Film film1 : films) {
            filmVoArrayList.add(copy(film1));
        }
        return filmVoArrayList;
    }
}
