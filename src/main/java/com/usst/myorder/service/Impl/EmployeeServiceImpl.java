package com.usst.myorder.service.Impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Employee;
import com.usst.myorder.mapper.EmployeeMapper;
import com.usst.myorder.service.EmployeeService;
import com.usst.myorder.util.JWTUtils;
import com.usst.myorder.vo.ErrorCode;
import com.usst.myorder.vo.LoginUserVo;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.EmployeeVo;
import com.usst.myorder.vo.params.PasswordParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Lazy
public class EmployeeServiceImpl implements EmployeeService {
    private static final String slat = "hdglxt!@#";
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public Employee findUser(String account, String password) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,account);
        queryWrapper.eq(Employee::getPassword,password);
        queryWrapper.last("limit 1");
        return employeeMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        Employee employee = this.checkToken(token);
        if (employee == null ||employee.getAdmin() == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(employee.getId()));
        loginUserVo.setUsername(employee.getUsername());
        loginUserVo.setNickname(employee.getName());
        return Result.succ(loginUserVo);
    }

    @Override
    public Employee checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
//        从redis中获取token
        String userJson = redisTemplate.opsForValue().get("AdminTOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        Employee employee = JSON.parseObject(userJson, Employee.class);
        return employee;
    }

    @Override
    public int update(Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        return employeeMapper.updateById(employee);
    }


    @Override
    public int delete(List<Long> ids) {
        return employeeMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return employeeMapper.deleteById(id);
    }

    @Override
    public Employee getById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Employee> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        userPage = employeeMapper.selectPage(userPage, null);
        List<Employee> list = userPage.getRecords();
        List<EmployeeVo> employeeVos = copyList(list);
        map.put("total",employeeMapper.selectCount(null));
        map.put("data",employeeVos);
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<Employee> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if (select.equals("idNumber")){
            select="id_number";
        }
        queryWrapper.like(select,input);
        userPage = employeeMapper.selectPage(userPage, queryWrapper);
        List<Employee> list = userPage.getRecords();
        List<EmployeeVo> sysUserVos = copyList(list);
        map.put("total",employeeMapper.selectCount(queryWrapper));
        map.put("data",sysUserVos);
        return map;
    }

    @Override
    public Integer save(Employee employee) {
        employeeMapper.updateautoid();
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        return employeeMapper.insert(employee);
    }

    @Override
    public Result changePassword(String token, PasswordParam passwordParam) {
        Employee sysUser = this.checkToken(token);
        redisTemplate.delete("TOKEN_"+token);
        String oldPassword = passwordParam.getOldPassword();
        oldPassword = DigestUtils.md5Hex(oldPassword+slat);
        String confirmPassword = sysUser.getPassword();
        if(!oldPassword.equals(confirmPassword)) {
            return Result.fail(ErrorCode.PASSWORD_ERROR.getCode(), ErrorCode.PASSWORD_ERROR.getMsg());
        }
        String newPassword = DigestUtils.md5Hex(passwordParam.getNewPassword() + slat);
        sysUser.setPassword(newPassword);
        UpdateWrapper<Employee> wrapper = new UpdateWrapper();
        wrapper.eq("id",sysUser.getId()+"");
        this.employeeMapper.update(sysUser,wrapper);
        return Result.succ("密码修改成功");
    }

    private EmployeeVo copy(Employee sysUser){
        EmployeeVo employeeVo = new EmployeeVo();
        employeeVo.setId(String.valueOf(sysUser.getId()));
        BeanUtils.copyProperties(sysUser, employeeVo);
        return employeeVo;
    }

    private List<EmployeeVo> copyList(List<Employee> sysUsers){
        ArrayList<EmployeeVo> employeeVoArrayList = new ArrayList<>();
        for (Employee sysUser : sysUsers) {
            employeeVoArrayList.add(copy(sysUser));
        }
        return employeeVoArrayList;
    }
}
