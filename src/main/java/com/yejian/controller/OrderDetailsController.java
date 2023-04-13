package com.yejian.controller;

import com.yejian.bean.OrderDetails;
import com.yejian.bean.query.OrderDetailsQuery;
import com.yejian.service.OrderDetailsService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("orderDetail")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    /**
     * 分页条件查询订单详情列表
     *
     * @param orderDetailsQuery
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("lists")
    public ResultInfo queryOrderDetailsByParams(@RequestBody OrderDetailsQuery orderDetailsQuery){
        Map<String, Object> map = orderDetailsService.queryOrderDetailsByParams(orderDetailsQuery);
        if (null==map){
            return ResultInfo.error("查询出错啦");
        }else {
            return ResultInfo.ok(map,"查询成功");
        }
    }

    /**
     * 添加子项记录
     * @param orderDetails 子项数据
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addOrderInfo")
    public ResultInfo addOrderInfo(@RequestBody OrderDetails orderDetails){
//        System.out.println(orderDetails.getOrderId());
//        System.out.println(orderDetails.getGoodsName());
//        System.out.println(orderDetails.getGoodsNum());
//        System.out.println(orderDetails.getSum());
//        System.out.println(orderDetails.getPrice());
//        System.out.println(orderDetails.getUnit());
        Integer integer = orderDetailsService.addOrderInfo(orderDetails);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }

    @PostMapping("updateOrderDetails")
    public ResultInfo updateOrderInfo(@RequestBody OrderDetails orderDetails){
        System.out.println(orderDetails.getUnit());
        System.out.println(orderDetails.getOrderId());
        System.out.println(orderDetails.getGoodsName());
        System.out.println(orderDetails.getGoodsNum());
        System.out.println(orderDetails.getSum());
        System.out.println(orderDetails.getPrice());
        Integer integer = orderDetailsService.updateOrderInfo(orderDetails);
        if (integer<1){
            return ResultInfo.error("更新失败");
        }else {
            return ResultInfo.ok("更新成功");
        }
    }

    @PostMapping("deleteOrderDetails")
    public ResultInfo deleteOrderDetailsInfo(@RequestBody Integer id){
        System.out.println("id:--------------->"+id);
        Integer integer = orderDetailsService.deleteInfo(id);
        if (integer<1){
            return ResultInfo.error("删除失败");
        }else {
            return ResultInfo.ok("删除成功");
        }

    }
}
