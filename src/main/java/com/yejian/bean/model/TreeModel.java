package com.yejian.bean.model;

import lombok.Data;

/**
 * 树形菜单
 */
@Data
public class TreeModel {

    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked = false; // 复选框是否被勾选。如果是true，则勾选；如果是false，则不勾选


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
