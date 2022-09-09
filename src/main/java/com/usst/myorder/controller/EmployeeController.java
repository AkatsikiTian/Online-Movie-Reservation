package com.usst.myorder.controller;

import cn.hutool.crypto.SecureUtil;
import com.usst.myorder.entity.Employee;
import com.usst.myorder.service.EmployeeService;
import com.usst.myorder.vo.EmployeeVo;
import com.usst.myorder.vo.ErrorCode;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.PasswordParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private static final String slat = "hdglxt!@#";
    @Autowired
    EmployeeService employeeService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return employeeService.findUserByToken(token);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody Employee employee,@RequestHeader("Authorization") String token){
        int result;
        System.out.println(employee);
        if (employee.getId()!=null){
            result = employeeService.update(employee);
        }else{

            employee.setPassword(SecureUtil.md5(employee.getPassword()+slat));
            result=employeeService.save(employee);
        }
        return Result.succ(result);
    }

    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids,@RequestHeader("Authorization") String token){
        return Result.succ(employeeService.delete(ids));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id,@RequestHeader("Authorization") String token){
        Employee employee = employeeService.checkToken(token);
        if (employee!=null){
            if (employee.getAdmin()!=0){
                return Result.fail(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg());
            }
        }
        return Result.succ(employeeService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        System.out.println("user getbyid"+id);
        return Result.succ(employeeService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(employeeService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(employeeService.findbyinput(currentPage,pageSize,select,input));
    }

    @PostMapping("changepassword")
    public Result changepassword(@RequestHeader("Authorization") String token, @RequestBody PasswordParam passwordParam){
        return employeeService.changePassword(token,passwordParam) ;
    }
}
