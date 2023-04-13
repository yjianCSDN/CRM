package com.yejian.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 客户订单
 * @Author
 * @Date
 */
@Data
public class CustomerServeOrder {
    private Integer id;
    private String khno;
    private Integer cusId;
    private String name;
    private String level;
    private String cusManager;
    private Integer isValid;
    private Integer orderId;
    private String orderNo;
    private String phone;
    private String address;
    private Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 如果传递的参数是Date类型，要求传入的时间字符串的格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}
