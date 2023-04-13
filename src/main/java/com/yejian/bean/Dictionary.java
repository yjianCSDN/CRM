package com.yejian.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 字典
 * @Author
 * @Date
 */
@Data
public class Dictionary {
    private Integer id;
    private String dataDicName;
    private String dataDicValue;
    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}
