package com.usst.myorder.controller;



import com.usst.myorder.cache.Cache;
import com.usst.myorder.entity.Film;
import com.usst.myorder.service.FilmService;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Autowired
    FilmService filmService;
    @PostMapping
    public Result save(@Validated @RequestBody Film film){
        int result;
        if (film.getId()!=null){
            result = filmService.update(film);
        }else{
            result= filmService.save(film);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        int delete = filmService.delete(ids);
        Result result = Result.succ(delete);
        if (delete==0){
            return Result.fail(400,"有管理员");
        }
        return result;
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        if (filmService.delete(id)==0){
            return Result.fail(400,"有管理员");
        }
        return Result.succ(filmService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(filmService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(filmService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(filmService.findbyinput(currentPage,pageSize,select,input));
    }

    @PostMapping("/search")
    public Result search(@RequestBody String search){
        return Result.succ(filmService.searchFilm(search));
    }

    @GetMapping("/getall")
    public Result getall(){
        return Result.succ(filmService.getall());
    }

    @Cache(expire = 5*60*1000,name = "hotfilm")
    @GetMapping("/hotfilm")
    public Result hotfilm(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(filmService.hotFilm(currentPage,pageSize));
    }

    @GetMapping("/hotfilm5")
    public Result hotfilmlist(){
        return Result.succ(filmService.hotFilmlist());
    }

    @Cache(expire = 5*60*1000,name = "getfilm")
    @GetMapping("/getfilm")
    public Result getfilm(@RequestParam Integer currentPage,Integer pageSize,Integer categoryId){
        return Result.succ(filmService.getFilm(currentPage,pageSize,categoryId));
    }

}
