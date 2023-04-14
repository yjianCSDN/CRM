package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.CustomerServeOrder;
import com.yejian.bean.query.CustomerQuery;
import com.yejian.mapper.CustomerServeOrderMapper;
import com.yejian.service.CustomerServeOrderService;
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
public class CustomerServeOrderServiceImpl extends BaseService<CustomerServeOrder,Integer> implements CustomerServeOrderService {
    @Autowired
    private CustomerServeOrderMapper customerServeOrderMapper;


    @Override
    public Map<String, Object> selectParams(CustomerQuery customerQuery) {

        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        PageInfo<CustomerServeOrder> pageInfo = new PageInfo<>(customerServeOrderMapper.selectByParams(customerQuery));
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @Override
    public Integer addCustomerList(CustomerServeOrder customerServeOrder) {

        customerServeOrder.setCreateDate(new Date());
        customerServeOrder.setUpdateDate(new Date());
        customerServeOrder.setIsValid(1);
        Integer integer = customerServeOrderMapper.addOrder(customerServeOrder);
        return integer;
    }
}
