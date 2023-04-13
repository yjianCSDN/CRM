package com.yejian.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author
 * @Date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PwdModel {

    private String userIdStr;

    private String oldPwd;

    private String newPwd;
}
