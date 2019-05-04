package org.woodwhales.king.model;

import lombok.Data;

@Data
public class Permission {
    private Integer pid;
    // 这里设置 权限的名称是了联合查询的时候，权限名称字段名和其他表中的字段名不冲突，保证唯一性
    private String rname;
    private String url;
}
