package com.usst.myorder.controller;



import com.usst.myorder.entity.House;
import com.usst.myorder.service.HouseService;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("house")
public class HouseController {
    @Autowired
    HouseService houseService;
    @PostMapping
    public Result save(@RequestBody House house){
        int result;
        if (house.getId()!=null){
            result = houseService.update(house);
        }else{
            result=houseService.save(house);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        return Result.succ(houseService.delete(ids));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        return Result.succ(houseService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(houseService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(houseService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/all")
    public Result getall(){
        return Result.succ(houseService.getall());
    }
}
