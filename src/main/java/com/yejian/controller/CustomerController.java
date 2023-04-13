package com.yejian.controller;

import com.yejian.bean.Customer;
import com.yejian.bean.query.CustomerQuery;
import com.yejian.service.CustomerService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 分页条件查询客户列表
     *
     * @param customerQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("lists")
    public ResultInfo queryCustomerByParams(CustomerQuery customerQuery){
        Map<String, Object> map = customerService.queryCustomerByParams(customerQuery);
        if (null==map){
            return ResultInfo.error("查询失败");
        }
            return ResultInfo.ok(map,"查询成功!");
    }

    /**
     * 添加客户信息
     *
     * @param customer
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addCustomer")
    @ResponseBody
    public ResultInfo addCustomer(@RequestBody Customer customer) {
//        System.out.println(customer.getCusManager());
//        System.out.println(customer.getAddress());
//        System.out.println(customer.getArea());
//        System.out.println(customer.getKhno());
//        System.out.println(customer.getDsdjh());
//        System.out.println(customer.getFax());
//        System.out.println(customer.getDsdjh());
//        System.out.println(customer.getGsdjh());
//        System.out.println(customer.getFr());
//        System.out.println(customer.getName());
//        System.out.println(customer.getKhzh());
//        System.out.println(customer.getKhyh());
//        System.out.println(customer.getLevel());
        Integer integer = customerService.addCustomer(customer);
        if (integer<1){
            return ResultInfo.error("添加失败!");
        }else {
            return ResultInfo.ok("添加客户信息成功！");
        }
    }

    /**
     * 修改客户信息
     *
     * @param customer
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("updateCustomer")
    public ResultInfo updateCustomer(@RequestBody Customer customer){
        System.out.println("-----------------------------------");
        System.out.println(customer.getArea());
        System.out.println(customer.getAddress());
        System.out.println(customer.getDsdjh());
        System.out.println(customer.getKhno());
        System.out.println(customer.getDsdjh());
        System.out.println(customer.getFax());
        System.out.println(customer.getFr());
        System.out.println(customer.getGsdjh());
        System.out.println(customer.getName());
        System.out.println(customer.getKhzh());
        System.out.println(customer.getKhyh());
        System.out.println(customer.getLevel());
        Integer integer = customerService.updateCustomer(customer);
        if (integer<1){
            return ResultInfo.error("更新失败");
        }else {
            return ResultInfo.ok("更新成功!");
        }
    }

    /**
     * 删除客户信息
     *
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("deleteCustomer")
    public ResultInfo deleteCustomer(@RequestBody Integer id){
//        System.out.println("id:------------->"+id);
        Integer integer = customerService.deleteCustomer(id);
        if (integer<1){
            return ResultInfo.error("删除失败");
        }else {
            return ResultInfo.ok("删除成功,请重试!");
        }
//        return ResultInfo.ok("123");
    }

    /**
     * 客户贡献分析
     *
     * @param customerQuery
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("queryCustomerContributionByParams")
    public ResultInfo queryCustomerContributionByParams(@RequestBody CustomerQuery customerQuery){
        Map<String, Object> map = customerService.queryCustomerContributionByParams(customerQuery);
        if (null==map){
            return ResultInfo.error("客户贡献分析查询失败");
        }else {
            return ResultInfo.ok(map,"客户贡献分析查询成功!");
        }
    }

    /**
     * 查询客户构成 （折线图）
     *
     * @param
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("countCustomerMake")
    public ResultInfo countCustomerMake(){
        Map<String, Object> map = customerService.countCustomerMake();
        if (null==map){
            return ResultInfo.error("查询客户构成 （折线图）失败!");
        }else{
            return ResultInfo.ok(map,"查询成功");
        }
    }

    /**
     * 查询客户构成 （饼状图）
     *
     * @param
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("countCustomerMake02")
    public ResultInfo countCustomerMake02(){
        Map<String, Object> map = customerService.countCustomerMake02();
        if (null==map){
            return ResultInfo.error("查询客户构成 （饼状图）失败!");
        }else{
            return ResultInfo.ok(map,"查询成功");
        }
    }

}
