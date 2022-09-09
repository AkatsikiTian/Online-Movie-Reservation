package com.usst.myorder.service;


import com.usst.myorder.entity.User;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.PasswordParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    Integer save(User user);
    User getById(@PathVariable Long id);
    List<User> getAll();
    String update(@RequestBody User user);
    Integer delete(@PathVariable Long id);
    Integer delete(@PathVariable List<Long> ids);
    Map<String,Object> findbypage(Integer currentPage,Integer pageSize);
    Map<String,Object> findbyinput(Integer currentPage,Integer pageSize,String select,String input);
    User findUser(String account, String password);
    Result findUserByToken(String token);
    User checkToken(String token);

    Result changePassword(String token, PasswordParam passwordParam);

    Result savePicture(MultipartFile file) throws IOException;
}
