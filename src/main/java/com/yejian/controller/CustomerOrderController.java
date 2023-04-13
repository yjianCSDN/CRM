package com.yejian.controller;

import com.yejian.bean.CustomerOrder;
import com.yejian.bean.query.CustomerOrderQuery;
import com.yejian.service.CustomerOrderService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customerOrder")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;

    /**
     * 分页多条件查询客户订单列表
     *
     * @param customerOrderQuery
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("lists")
    public ResultInfo queryCustomerOrderByParams(@RequestBody CustomerOrderQuery customerOrderQuery){
        Map<String, Object> map = customerOrderService.queryCustomerOrderByParams(customerOrderQuery);
        if (null==map){
            return ResultInfo.error("查询错误");
        }else {
            return ResultInfo.ok(map,"查询成功!");
        }
    }

    /**
     * 通过订单ID查询对应的订单记录
     *
     * @param orderId
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("orderDetail")
    public ResultInfo OrderDetailPage(Integer orderId){
        Map<String, Object> map = customerOrderService.queryOrderById(orderId);
        if (null==map){
            return ResultInfo.error("查询出错啦!");
        }else {
            return ResultInfo.ok(map,"查询成功!");
        }
    }

    /**
     * 添加订单
     * @param customerOrder
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addOrder")
    public ResultInfo addOrders(@RequestBody CustomerOrder customerOrder){
        Integer integer = customerOrderService.addOrders(customerOrder);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }


    /**
     * 修改支付状态
     * @param id
     * @return
     */
    @GetMapping("setStateIsPay")
    public ResultInfo setState(Integer id){
        Integer integer = customerOrderService.setStateIsPay(id);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }

    @PostMapping("updateInfo")
    public ResultInfo updateInfo(@RequestBody CustomerOrder customerOrder){
        System.out.println("updateInfo被调用了");
        Integer integer = customerOrderService.updateCustomerInfo(customerOrder);
        if (integer<1){
            return ResultInfo.error("修改失败");
        }else {
            return ResultInfo.ok("修改成功");
        }
    }
}
