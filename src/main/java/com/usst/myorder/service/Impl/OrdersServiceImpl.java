package com.usst.myorder.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.myorder.entity.*;
import com.usst.myorder.entity.Orders;
import com.usst.myorder.entity.Orders;
import com.usst.myorder.mapper.OrdersMapper;
import com.usst.myorder.mapper.UserMapper;
import com.usst.myorder.service.ArrangementService;
import com.usst.myorder.service.OrdersService;
import com.usst.myorder.vo.FilmVo;
import com.usst.myorder.vo.OrderVo;
import com.usst.myorder.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private Queue queue;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public int update(Orders orders) {
        return ordersMapper.updateById(orders);
    }

    @Override
    public int delete(List<Long> ids) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public OrderVo getById(Long id) {
        return copy(ordersMapper.selectById(id));
    }

    @Override
    public List<OrderVo> getByuserId(Long id) {
        List<Orders> orders = ordersMapper.selectList(new QueryWrapper<Orders>().eq("user_id", id).orderByDesc("order_time"));
        return copyList(orders);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Orders> ordersPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        ordersPage = ordersMapper.selectPage(ordersPage, new QueryWrapper<Orders>().orderByDesc("order_time"));
        List<Orders> list = ordersPage.getRecords();
        map.put("total",ordersMapper.selectCount(null));
        map.put("data",copyList(list));
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<Orders> ordersPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(select,input);
        queryWrapper.orderByDesc("order_time");
        ordersPage = ordersMapper.selectPage(ordersPage, queryWrapper);
        List<Orders> list = ordersPage.getRecords();
        map.put("total",ordersMapper.selectCount(queryWrapper));
        map.put("data",copyList(list));
        return map;
    }

    @Override
    public Integer save(Orders orders) {
        ordersMapper.updateautoid();
        orders.setOrderTime(LocalDateTime.now());
        String front="USST";//前缀
        //当前时间编码
        Date date = new Date();
        String bidDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        orders.setNumber(front+bidDate);
        jmsMessagingTemplate.convertAndSend(this.queue,JSONObject.toJSONString(orders));
        return 1;
    }

    @Override
    public List<OrderVo> getall() {
        return null;
    }

    @Override
    public Double sum() {
        Double getsum = ordersMapper.getsum();
        return getsum;
    }

    @Override
    public Map<String, Object> fivedayorders() {
        Map<String, Object> map = new HashMap<>();
        Integer gettoday = ordersMapper.gettoday();
        Integer gety = ordersMapper.gety();
        Integer getyy = ordersMapper.getyy();
        Integer getyyy = ordersMapper.getyyy();
        Integer getyyyy = ordersMapper.getyyyy();
        Date d=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        map.put("xAxis", CollUtil.newArrayList(df.format(new Date(d.getTime() - 4 * 24 * 60 * 60 * 1000)),df.format(new Date(d.getTime() - 3 * 24 * 60 * 60 * 1000)),df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000))
                ,df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000)),df.format(d)));
        map.put("data", CollUtil.newArrayList(getyyyy,getyyy,getyy,gety,gettoday));
        return map;
    }

    private OrderVo copy(Orders orders){
        OrderVo orderVo = new OrderVo();
        orderVo.setId(String.valueOf(orders.getId()));
        orderVo.setArrangementVo(arrangementService.getById(orders.getAid()));
        orderVo.setUser(userMapper.selectById(orders.getUserId()));
        BeanUtils.copyProperties(orders, orderVo);
        return orderVo;
    }

    private List<OrderVo> copyList(List<Orders> orders){
        ArrayList<OrderVo> OrderVoArrayList = new ArrayList<>();
        for (Orders orders1 : orders) {
            OrderVoArrayList.add(copy(orders1));
        }
        return OrderVoArrayList;
    }
}
