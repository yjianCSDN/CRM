package com.yejian;

import com.yejian.bean.Notice;
import com.yejian.bean.query.CustomerServeQuery;
import com.yejian.bean.query.SaleChanceQuery;
import com.yejian.bean.query.UserQuery;
import com.yejian.service.*;
import com.yejian.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class CrmApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CustomerServeService customerServeService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CusDevPlanService cusDevPlanService;

    @Test
    void contextLoads() {
        Map<String, Object> map = customerServeService.countCustomerMake02();
        if (null==map) {
            System.out.println(1);
        }else {
            System.out.println(2);
        }
    }

    @Test
    void mm(){
        String s = MD5Utils.md5Encrypt("123456");
        System.out.println(s);
        String s1 = MD5Utils.md5Decrypt(s);
        System.out.println("s1" + s1);
        String s2 = MD5Utils.md5ToSecret("123456");
        System.out.println("s2" + s2);
        String s3 = MD5Utils.md5Decrypt(s2);
        System.out.println("s3  " + s3);
    }
    @Test
    void aa(){
        SaleChanceQuery query = new SaleChanceQuery();
//        query.setPage(0);
//        query.setLimit(10);
        Map<String, Object> map = saleChanceService.querySaleChanceByParams(query);
        System.out.println(map);
    }
    @Test
    void b(){
        Notice notice = new Notice();
        notice.setType("正常");
        notice.setPublisher("admin");
        notice.setContent("今天下班时间提前30分钟，销售人员进行客户调研");
        notice.setUserId(93);
//        notice.setId(20);
        notice.setTitle("乐");
//        Integer integer = noticeService.delNotice(20);
        Integer integer = noticeService.addNotice(notice);
        System.out.println(integer);
    }

    @Test
    void c(){
        System.out.println(userService.queryByParamsForTable(new UserQuery()));
    }

    @Test
    void d(){
        System.out.println(customerService.getCusPhones());
    }

    @Test
    void f(){
        System.out.println(customerServeService.queryCustomerServeByParams(new CustomerServeQuery()));
    }
    @Test
    void g(){
        Map<String, Object> map = customerService.countOrderByCustomer("");
        System.out.println(map);
    }
    @Test
    void dd(){
        CustomerServeQuery query = new CustomerServeQuery();
        query.setAuditStatus(0);
        query.setCreatePeople("admin");
        System.out.println(customerServeService.queryCustomerServeByParams(query));
    }
    @Test
    void ss(){
    }



}
