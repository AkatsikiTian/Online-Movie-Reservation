package com.usst.myorder.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Employee;
import com.usst.myorder.entity.User;
import com.usst.myorder.mapper.UserMapper;
import com.usst.myorder.service.UserService;
import com.usst.myorder.util.JWTUtils;
import com.usst.myorder.vo.ErrorCode;
import com.usst.myorder.vo.LoginUserVo;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.PasswordParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static final String slat = "hdglxt!@#";

    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Integer save(User user) {
        userMapper.updateautoid();
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insert(user);

    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectList(null);
    }

    @Override
    public String update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("AdminTOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Integer delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Integer delete(List<Long> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage,Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<User> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        userPage = userMapper.selectPage(userPage, null);
        List<User> list = userPage.getRecords();
        map.put("total",userMapper.selectCount(null));
        map.put("data",list);
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize,String select,String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<User> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(select,input);
        userPage = userMapper.selectPage(userPage, queryWrapper);
        List<User> list = userPage.getRecords();
        map.put("total",userMapper.selectCount(queryWrapper));
        map.put("data",list);
        return map;
    }


    @Override
    public User findUser(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,account);
        queryWrapper.eq(User::getPassword,password);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        User user = this.checkToken(token);
        if (user == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(user.getId()));
        loginUserVo.setUsername(user.getUsername());
        loginUserVo.setNickname(user.getNickname());
        loginUserVo.setAvatar(user.getAvatar());
        return Result.succ(loginUserVo);
    }

    @Override
    public User checkToken(String token) {
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
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }

    @Override
    public Result changePassword(String token, PasswordParam passwordParam) {
        System.out.println(token);
        User sysUser = this.checkToken(token);
        System.out.println(sysUser);
        String oldPassword = passwordParam.getOldPassword();
        oldPassword = DigestUtils.md5Hex(oldPassword+slat);
        String confirmPassword = userMapper.selectById(sysUser.getId()).getPassword();
        System.out.println(oldPassword);
        System.out.println(confirmPassword);
        if(!oldPassword.equals(confirmPassword)) {
            return Result.fail(ErrorCode.PASSWORD_ERROR.getCode(), ErrorCode.PASSWORD_ERROR.getMsg());
        }
        String newPassword = DigestUtils.md5Hex(passwordParam.getNewPassword() + slat);
        sysUser.setPassword(newPassword);
        UpdateWrapper<User> wrapper = new UpdateWrapper();
        wrapper.eq("id",sysUser.getId()+"");
        userMapper.update(sysUser,wrapper);
        redisTemplate.delete("AdminTOKEN_"+token);
        return Result.succ("密码修改成功");
    }

    @Override
    public Result savePicture(MultipartFile file) throws IOException {
        String originalFilenamePath = file.getOriginalFilename();
        String sysPath = ResourceUtils.getURL("classpath:").getPath();
        int index = sysPath.indexOf("target");
        sysPath = sysPath.substring(0,index)+"backgroundmanagement/public/" +"static/avatar";
        sysPath = sysPath.replace('/', '\\').substring(1,sysPath.length());
        String realPath = "/static/avatar/"+originalFilenamePath;
        File dest = new File(sysPath,originalFilenamePath);
        file.transferTo(dest);
        return Result.succ(realPath);
    }
}
