package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 *
 */
@Data
public class CustomerQuery extends BaseQuery {

    private String customerName; // 客户名称
    private String customerNo; // 客户编号
    private String level; // 客户级别

    private String time; // 订单时间
    private Integer type; // 金额区间  1=1-1000 2=1000-3000  3=3000-5000  4=5000以上


}
