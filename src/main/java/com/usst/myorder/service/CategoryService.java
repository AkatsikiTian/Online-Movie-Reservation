package com.usst.myorder.service;

import com.usst.myorder.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    int update(Category category);

    int delete(List<Long> ids);

    int delete(Long id);

    Category getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Integer save(Category category);

    List<Category> getall0();
}
