package com.yejian.bean.query;


import com.yejian.utils.BaseQuery;
import lombok.Data;


@Data
public class CustomerReprieveQuery extends BaseQuery {

    // 流失客户ID
    private Integer lossId;

}
