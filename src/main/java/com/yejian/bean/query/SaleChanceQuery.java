package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * 营销机会的查询类
 * 多条件查询的几个条件
 */
@Data
public class SaleChanceQuery extends BaseQuery {

    // 分页参数

    // 营销机会管理 条件查询
    private String customerName; // 客户名
    private String createMan; // 创建人
    private Integer state; // 分配状态  0=未分配 1=已分配

    // 客户开发计划 条件查询
    private String devResult; // 开发状态
    private String assignMan; // 指派人


}
