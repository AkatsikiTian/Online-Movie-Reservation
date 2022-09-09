package com.usst.myorder.controller;


import com.usst.myorder.entity.Category;
import com.usst.myorder.service.CategoryService;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping
    public Result save(@Validated @RequestBody Category category){
        int result;
        System.out.println(category);
        if (category.getId()!=null){
            result = categoryService.update(category);
        }else{
            result=categoryService.save(category);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        return Result.succ(categoryService.delete(ids));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        return Result.succ(categoryService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(categoryService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(categoryService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/category0")
    public Result getallcategory(){
        return Result.succ(categoryService.getall0());
    }
}
