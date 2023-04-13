package com.yejian.bean.query;

import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * @Author
 * @Date
 */
@Data
public class ModuleQuery extends BaseQuery {
    private String MenuName;
    private Integer grade;
}
