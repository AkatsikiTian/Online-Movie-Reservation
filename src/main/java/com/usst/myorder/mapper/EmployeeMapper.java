package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.Employee;
import org.apache.ibatis.annotations.Update;

public interface EmployeeMapper extends BaseMapper<Employee> {
    @Update("alter table order.employee AUTO_INCREMENT = 1")
    Integer updateautoid();
}
