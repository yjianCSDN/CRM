package com.yejian;

import com.yejian.bean.query.DictionaryQuery;
import com.yejian.bean.query.SaleChanceQuery;
import com.yejian.service.CustomerServeService;
import com.yejian.service.DictionaryService;
import com.yejian.service.SaleChanceService;
import com.yejian.service.UserService;
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
    private DictionaryService dictionaryService;

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
        Map<String, Object> map = dictionaryService.queryByParams(new DictionaryQuery());
        System.out.println(map.isEmpty());
        System.out.println(map);
    }

    @Test
    void c(){
//        dictionaryService.
    }


}
