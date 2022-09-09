package com.usst.myorder.service;

import com.usst.myorder.entity.Orders;
import com.usst.myorder.vo.OrderVo;
import com.usst.myorder.vo.Result;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrdersService {
    int update(Orders orders);

    int delete(List<Long> ids);

    int delete(Long id);

    OrderVo getById(Long id);

    List<OrderVo> getByuserId(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);

    Integer save(Orders orders);

    List<OrderVo> getall();

    Double sum();

    Map<String, Object> fivedayorders();
}
