package com.usst.myorder.controller;


import com.usst.myorder.entity.Orders;
import com.usst.myorder.service.OrdersService;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("Orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @PostMapping
    public Result save( @RequestBody Orders orders){
        int result;
        if (orders.getId()!=null){
            result = ordersService.update(orders);
        }else{
            result= ordersService.save(orders);
        }
        return Result.succ(result);
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        return Result.succ(ordersService.getById(id));
    }

    @GetMapping("/user/{id}")
    public Result getByuserId(@PathVariable Long id){
        return Result.succ(ordersService.getByuserId(id));
    }

    @GetMapping("load")
    public Result getall(){
        return Result.succ(ordersService.getall());
    }

    @GetMapping("fivedayorders")
    public Result getfivedayorders(){
        return Result.succ(ordersService.fivedayorders());
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(ordersService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(ordersService.findbyinput(currentPage,pageSize,select,input));
    }

    @GetMapping("/sum")
    public Result sum(){
        return Result.succ(ordersService.sum());
    }
}
