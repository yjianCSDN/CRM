package com.yejian.controller;

import com.yejian.bean.Customer;
import com.yejian.bean.CustomerServe;
import com.yejian.bean.query.CustomerServeQuery;
import com.yejian.service.CustomerServeService;
import com.yejian.utils.LoginUserUtil;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customerServe")
public class CustomerServeController {
    @Autowired
    private CustomerServeService customerServeService;

    /**
     * 多条件分页查询服务数据的列表
     *
     * @param customerServeQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo queryCustomerServeByParams(CustomerServeQuery customerServeQuery,
                                                 Integer flag, HttpServletRequest request) {

        System.out.println("flag"+flag);
        System.out.println("getCustomer"+customerServeQuery.getCustomer());
        System.out.println("getServeType"+customerServeQuery.getServeType());
        int id = LoginUserUtil.releaseUserIdFromCookie(request);
        System.out.println("id:    ----->"+id);
        // 判断是否执行服务处理，如果是则查询分配给当前登录用户的服务记录
        if (flag != null && flag == 1) {
            // 设置查询条件：分配人
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }

        Map<String, Object> map = customerServeService.queryCustomerServeByParams(customerServeQuery);
        if (null==map){
            return ResultInfo.error("查找失败");
        }else {
            return ResultInfo.ok(map,"查找成功");
        }
    }

    /**
     * 创建服务
     *
     *
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @param customerServe
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("add")
    public ResultInfo addCustomerServe(@RequestBody CustomerServe customerServe, HttpServletRequest request){
        System.out.println("headers :"+request.getHeader("userName"));
        System.out.println(customerServe.getCustomer());
        System.out.println(customerServe.getServeType());
        System.out.println(customerServe.getDicValue());
        System.out.println(customerServe.getOverview());
        customerServe.setCreatePeople(request.getHeader("userName"));
//        System.out.println(customerServe.getCreatePeople()+"-----------------------<>");
        Integer integer = customerServeService.addCustomerServe(customerServe);
        if (integer<1){
            return ResultInfo.error("创建失败");
        }else {
            return ResultInfo.ok("创建成功!");
        }
    }

    /**
     * 检查客户是否在客户表中
     *
     * 客户表中存在客户记录
     * @param customer
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("getInfoByName")
    public ResultInfo getInfoByName(String customer){
        Customer customer1 = customerServeService.getInfoByName(customer);
        if (null==customer1) {
            return ResultInfo.error("暂无该用户数据");
        }else {
            return ResultInfo.ok(customer1,"有!");
        }
    }


    /**
     * 服务更新
     *     1. 服务分配
     *     2. 服务处理
     *     3.服务反馈
     *
     * @param customerServe
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("update")
    public ResultInfo updateCustomerServe(@RequestBody CustomerServe customerServe){
        System.out.println(customerServe.getCustomer());
        System.out.println(customerServe.getServeType());
        System.out.println(customerServe.getDicValue());
        System.out.println(customerServe.getOverview());
        Integer integer = customerServeService.updateCustomerServe(customerServe);
        if (integer<1){
            return ResultInfo.error("创建失败");
        }else {
            return ResultInfo.ok("创建成功!");
        }
    }

    /**
     * 查询客户服务构成 （折线图）
     *
     * @param
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("countCustomerMake02")
    public ResultInfo countCustomerMake02(){
        Map<String, Object> map = customerServeService.countCustomerMake02();
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
    @GetMapping("countCustomerMake")
    public ResultInfo countCustomerMake(){
        Map<String, Object> map = customerServeService.countCustomerMake();
        if (null==map){
            return ResultInfo.error("查询客户构成 （饼状图）失败!");
        }else{
            return ResultInfo.ok(map,"查询成功");
        }
    }
}
