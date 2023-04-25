package com.yejian.bean.query;

import com.yejian.utils.BaseQuery;
import lombok.Data;

/**
 * @Author
 * @Date
 */
@Data
public class NoticeQuery extends BaseQuery {
    private Integer userId;

    private String type;

    private String title;

    private String publisher;
}
