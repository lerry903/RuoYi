package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author ruoyi
 */
@Data
@ApiModel(description="角色和部门关联关系")
public class SysRoleDept implements Serializable {

    @ApiModelProperty(value="角色ID",name="roleId",example="1")
    private Long roleId;

    @ApiModelProperty(value="部门ID",name="deptId",example="1")
    private Long deptId;
}
