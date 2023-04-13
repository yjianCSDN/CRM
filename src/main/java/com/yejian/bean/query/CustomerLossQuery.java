package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * 客户流失管理查询条件
 */
@Data
public class CustomerLossQuery extends BaseQuery {

    private String customerNo; // 客户编号
    private String customerName; // 客户名称
    private Integer state; // 流失状态  0=暂缓流失状态  1=确认流失状态


}
