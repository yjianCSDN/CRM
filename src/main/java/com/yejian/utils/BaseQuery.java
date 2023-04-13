package com.yejian.utils;


import lombok.Data;

/**
 * 分页查询
 */
@Data
public class BaseQuery {
//    页码
    private Integer page=1;
//    限制条件
    private Integer limit=10;

}
