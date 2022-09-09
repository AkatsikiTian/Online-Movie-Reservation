package com.usst.myorder.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.Arrangement;
import com.usst.myorder.entity.Category;
import com.usst.myorder.entity.Film;
import com.usst.myorder.entity.Orders;
import com.usst.myorder.mapper.ArrangementMapper;
import com.usst.myorder.mapper.FilmMapper;
import com.usst.myorder.mapper.HouseMapper;
import com.usst.myorder.mapper.OrdersMapper;
import com.usst.myorder.service.ArrangementService;
import com.usst.myorder.vo.ArrangementVo;
import com.usst.myorder.vo.FilmVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArrangementServiceImpl implements ArrangementService {
    @Autowired
    FilmMapper filmMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    ArrangementMapper arrangementMapper;
    @Autowired
    OrdersMapper ordersMapper;

    @Override
    public int update(Arrangement arrangement) {
        arrangement.setUpdateTime(LocalDateTime.now());
        return arrangementMapper.updateById(arrangement);
    }

    @Override
    public int delete(List<Long> ids) {
        return arrangementMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return arrangementMapper.deleteById(id);
    }

    @Override
    public ArrangementVo getById(Long id) {
        return copy(arrangementMapper.selectById(id));
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Arrangement> arrangementIPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        arrangementIPage = arrangementMapper.selectPage(arrangementIPage, null);
        List<Arrangement> list = arrangementIPage.getRecords();
        List<ArrangementVo> arrangementVos = copyList(list);
        map.put("total", arrangementMapper.selectCount(null));
        map.put("data", arrangementVos);
        return map;
    }

    @Override
    public Integer save(Arrangement arrangement) {
        arrangementMapper.updateautoid();
        arrangement.setCreateTime(LocalDateTime.now());
        arrangement.setUpdateTime(LocalDateTime.now());
        return arrangementMapper.insert(arrangement);
    }

    @Override
    public Map<String,Object> getarrangement(Long id) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Arrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("filmid",id);
        List<Arrangement> list = arrangementMapper.selectList(queryWrapper);
        List<ArrangementVo> arrangementVos = copyList(list);
        map.put("total", arrangementVos.size());
        map.put("data", arrangementVos);
        return map;
    }

    @Override
    public List<Integer> getSeatsHaveSelected(String id) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>().in("aid", id);
        wrapper.ne("status",3);
        List<Orders> orders = ordersMapper.selectList(wrapper);
        List<Integer> seats = new ArrayList<>();
        for (Orders o : orders) {
            String[] split = o.getSeat().split("号");
            for (String s : split) {
                seats.add(Integer.parseInt(s));
            }
        }
        return seats;
    }

    private ArrangementVo copy(Arrangement arrangement){
        ArrangementVo arrangementVo = new ArrangementVo();
        arrangementVo.setFilmName(filmMapper.selectById(arrangement.getFilmid()).getName());
        arrangementVo.setHousename(houseMapper.selectById(arrangement.getHouseid()).getName());
        BeanUtils.copyProperties(arrangement, arrangementVo);
        return arrangementVo;
    }

    private List<ArrangementVo> copyList(List<Arrangement> arrangements){
        ArrayList<ArrangementVo> arrangementVoArrayList = new ArrayList<>();
        for (Arrangement arrangement : arrangements) {
            arrangementVoArrayList.add(copy(arrangement));
        }
        return arrangementVoArrayList;
    }
}
