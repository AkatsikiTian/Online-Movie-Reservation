package com.usst.myorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.myorder.entity.Orders;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrdersMapper extends BaseMapper<Orders> {
    @Update("alter table order.orders AUTO_INCREMENT = 1")
    Integer updateautoid();
    @Select("select sum(price) from order.orders where status!=3")
    Double getsum();
    @Select("select count(*) from order.orders where TO_DAYS(order_time)=TO_DAYS(NOW())-4;")
    Integer getyyyy();
    @Select("select count(*) from order.orders where TO_DAYS(order_time)=TO_DAYS(NOW())-3;")
    Integer getyyy();
    @Select("select count(*) from order.orders where TO_DAYS(order_time)=TO_DAYS(NOW())-2;")
    Integer getyy();
    @Select("select count(*) from order.orders where TO_DAYS(order_time)=TO_DAYS(NOW())-1;")
    Integer gety();
    @Select("select count(*) from order.orders where TO_DAYS(order_time)=TO_DAYS(NOW());")
    Integer gettoday();
}
