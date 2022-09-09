package com.usst.myorder.controller;

import com.usst.myorder.entity.Arrangement;
import com.usst.myorder.service.ArrangementService;
import com.usst.myorder.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("arrangement")
public class ArrangementController {
    @Autowired
    ArrangementService arrangementService;

    @PostMapping
    public Result save(@RequestBody Arrangement arrangement){
        int result;
        if (arrangement.getId()!=null){
            result = arrangementService.update(arrangement);
        }else{
            result=arrangementService.save(arrangement);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        return Result.succ(arrangementService.delete(ids));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        return Result.succ(arrangementService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(arrangementService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(arrangementService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/findbyinput/{id}")
    public Result getarrangement(@PathVariable Long id){
        return Result.succ(arrangementService.getarrangement(id));
    }

    @GetMapping("/getSeats")
    public Result getSeats(String id) {
        return Result.succ(arrangementService.getSeatsHaveSelected(id));
    }
}
