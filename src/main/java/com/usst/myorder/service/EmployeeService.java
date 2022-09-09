package com.usst.myorder.service;



import com.usst.myorder.entity.Employee;
import com.usst.myorder.vo.Result;
import com.usst.myorder.vo.params.PasswordParam;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee findUser(String account, String password);

    Result findUserByToken(String token);

    Employee checkToken(String token);

    int update(Employee employee);

    int delete(List<Long> ids);

    int delete(Long id);

    Employee getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);

    Integer save(Employee employee);

    Result changePassword(String token, PasswordParam passwordParam);
}
