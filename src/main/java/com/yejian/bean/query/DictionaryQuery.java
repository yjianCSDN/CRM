package com.yejian.bean.query;

import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * @Author
 * @Date
 */
@Data
public class DictionaryQuery extends BaseQuery {
    private String dataDicName;
    private String dataDicValue;
}
