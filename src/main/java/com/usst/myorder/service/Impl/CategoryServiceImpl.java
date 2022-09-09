package com.usst.myorder.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Category;
import com.usst.myorder.entity.Employee;
import com.usst.myorder.mapper.CategoryMapper;
import com.usst.myorder.service.CategoryService;
import com.usst.myorder.vo.EmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public int update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateById(category);
    }

    @Override
    public int delete(List<Long> ids) {
        return categoryMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Category> catePage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        catePage = categoryMapper.selectPage(catePage, null);
        List<Category> list = catePage.getRecords();
        map.put("total",categoryMapper.selectCount(null));
        map.put("data",list);
        return map;
    }



    @Override
    public Integer save(Category category) {
        categoryMapper.updateautoid();
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());
        return categoryMapper.insert(category);
    }

    @Override
    public List<Category> getall0() {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("type",0);
        return categoryMapper.selectList(qw);
    }
}
