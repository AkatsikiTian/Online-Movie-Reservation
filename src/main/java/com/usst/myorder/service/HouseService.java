package com.usst.myorder.service;



import com.usst.myorder.entity.House;

import java.util.List;
import java.util.Map;

public interface HouseService {
    int update(House house);

    int delete(List<Long> ids);

    int delete(Long id);

    House getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Integer save(House house);

    List<House> getall();
}
