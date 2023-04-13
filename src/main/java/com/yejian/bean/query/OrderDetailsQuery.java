package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;

/**
 *
 */
public class OrderDetailsQuery extends BaseQuery {

    private Integer orderId; // 订单ID

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
