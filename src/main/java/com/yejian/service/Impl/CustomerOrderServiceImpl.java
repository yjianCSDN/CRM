package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.CustomerOrder;
import com.yejian.bean.query.CustomerOrderQuery;
import com.yejian.mapper.CustomerOrderMapper;
import com.yejian.service.CustomerOrderService;
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
public class CustomerOrderServiceImpl extends BaseService<CustomerOrder,Integer> implements CustomerOrderService {

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 多条件分页查询客户
     *
     * @param customerOrderQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryCustomerOrderByParams(CustomerOrderQuery customerOrderQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerOrderQuery.getPage(), customerOrderQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerOrder> pageInfo = new PageInfo<>(customerOrderMapper.selectByParams(customerOrderQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }


    /***
     * 通过订单ID查询对应的订单记录
     *
     * @param orderId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryOrderById(Integer orderId) {
        return customerOrderMapper.queryOrderById(orderId);
    }

    /**
     * 添加订单
     * @param customerOrder
     * @return
     */
    @Override
    public Integer addOrders(CustomerOrder customerOrder) {
        customerOrder.setCreateDate(new Date());
        customerOrder.setUpdateDate(new Date());
//        // 创建时间
//        role.setCreateDate(new Date());
//        // 修改时间
//        role.setUpdateDate(new Date());
        Integer integer = customerOrderMapper.insertSelective(customerOrder);
        return integer;
    }

    @Override
    public Integer setStateIsPay(Integer id) {
        CustomerOrder order = customerOrderMapper.selectByPrimaryKey(id);
        order.setState(1);
        Integer integer = customerOrderMapper.updateByPrimaryKeySelective(order);
        return integer;
    }

    @Override
    public Integer updateCustomerInfo(CustomerOrder customerOrder) {
        CustomerOrder order = customerOrderMapper.selectByPrimaryKey(customerOrder.getId());
        order.setCusId(customerOrder.getCusId());
        order.setUpdateDate(new Date());
        order.setPhone(customerOrder.getPhone());
        order.setOrderNo(customerOrder.getOrderNo());
        order.setAddress(customerOrder.getAddress());
        order.setOrderDate(customerOrder.getOrderDate());
        Integer integer = customerOrderMapper.updateByPrimaryKeySelective(order);
        return integer;
    }
}
