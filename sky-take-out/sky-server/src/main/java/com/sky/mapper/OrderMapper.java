package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    Long insert(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);


    void update(Orders orders);

    @Select("select count(id) from orders where status = #{toBeConfirmed}")
    Integer countStatus(Integer toBeConfirmed);

    Double sumByMap(Map map);

    @Select("select * from orders where status = #{status} and order_time < #{orderTimeLimit}")
    List<Orders> getTimeOutOrderByStatusAndLimit(Integer status, LocalDateTime orderTimeLimit);
}
