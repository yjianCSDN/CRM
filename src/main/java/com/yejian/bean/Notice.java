package com.yejian.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author
 * @Date
 * 公告
 */
@Data
public class Notice {

    private Integer id;

    private Integer userId;

    private String publisher;

    private String title;

    private String type;

    private String content;

    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Integer isValid;
}
