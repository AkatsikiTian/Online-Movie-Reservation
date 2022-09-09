package com.usst.myorder.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Category;
import com.usst.myorder.entity.House;
import com.usst.myorder.mapper.HouseMapper;
import com.usst.myorder.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    HouseMapper houseMapper;

    @Override
    public int update(House house) {
        house.setUpdateTime(LocalDateTime.now());
        return houseMapper.updateById(house);
    }

    @Override
    public int delete(List<Long> ids) {
        return houseMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return houseMapper.deleteById(id);
    }

    @Override
    public House getById(Long id) {
        return houseMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<House> housePage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        housePage = houseMapper.selectPage(housePage, null);
        List<House> list = housePage.getRecords();
        map.put("total",houseMapper.selectCount(null));
        map.put("data",list);
        return map;
    }

    @Override
    public Integer save(House house) {
        houseMapper.updateautoid();
        house.setCreateTime(LocalDateTime.now());
        house.setUpdateTime(LocalDateTime.now());
        return houseMapper.insert(house);
    }

    @Override
    public List<House> getall() {
        return houseMapper.selectList(null);
    }
}
