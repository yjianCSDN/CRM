package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.OrderDetails;
import com.yejian.bean.query.OrderDetailsQuery;
import com.yejian.mapper.OrderDetailsMapper;
import com.yejian.service.OrderDetailsService;
import com.yejian.utils.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@Service
public class OrderDetailsServiceImpl extends BaseService<OrderDetails,Integer> implements OrderDetailsService {

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    /***
     * 分页条件查询订单详情列表
     *
     * @param orderDetailsQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailsQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(orderDetailsQuery.getPage(), orderDetailsQuery.getLimit());
        // 得到对应分页对象
        PageInfo<OrderDetails> pageInfo = new PageInfo<>(orderDetailsMapper.selectByParams(orderDetailsQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 添加子项
     * @param orderDetails
     * @return
     */
    @Override
    public Integer addOrderInfo(OrderDetails orderDetails) {
        //设置子项存活状态、更新时间和创造时间（新增默认创造时间为new Date()）
        orderDetails.setIsValid(1);
        orderDetails.setCreateDate(new Date());
        orderDetails.setUpdateDate(new Date());
        Integer integer = orderDetailsMapper.insertSelective(orderDetails);
        return integer;
    }

    @Override
    public Integer updateOrderInfo(OrderDetails orderDetails) {
        orderDetails.setUpdateDate(new Date());
        Integer integer = orderDetailsMapper.updateByPrimaryKeySelective(orderDetails);
        return integer;
    }

    @Override
    public Integer deleteInfo(Integer id) {
        Integer integer = orderDetailsMapper.deleteInfo(id);

        return integer;
    }
}
