package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * 客户订单查询
 */
@Data
public class CustomerOrderQuery extends BaseQuery {

    private Integer cusId;  // 客户ID

}
