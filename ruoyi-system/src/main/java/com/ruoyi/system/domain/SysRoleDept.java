package com.ruoyi.system.domain;

import lombok.Data;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author ruoyi
 */
@Data
public class SysRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
