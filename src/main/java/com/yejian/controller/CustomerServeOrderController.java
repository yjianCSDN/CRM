package com.yejian.controller;

import com.yejian.bean.CustomerServeOrder;
import com.yejian.bean.query.CustomerQuery;
import com.yejian.service.CustomerServeOrderService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customerServeOrder")
public class CustomerServeOrderController {
    @Autowired
    private CustomerServeOrderService customerServeOrderService;

    /**
     * 获取顾客订单列表
     * @param customerQuery
     * @return
     */
    @GetMapping("/lists")
    public ResultInfo getList(CustomerQuery customerQuery){
//        System.out.println(customerQuery.getCustomerNo());
//        System.out.println(customerQuery.getCustomerName());
//        System.out.println(customerQuery.getLevel());
        Map<String, Object> map = customerServeOrderService.selectParams(customerQuery);
        if (null==map){
            return ResultInfo.error("查找出错");
        }else {
            return ResultInfo.ok(map,"查找成功");
        }
    }

    /**
     * 添加订单
     * @param customerServeOrder
     * @return
     */
    @PostMapping("addOrder")
    public ResultInfo addCustomerList(@RequestBody CustomerServeOrder customerServeOrder){
        Integer integer = customerServeOrderService.addCustomerList(customerServeOrder);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }

}
