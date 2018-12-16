package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author ruoyi
 */
@Data
@ApiModel(description="角色和菜单关联关系")
public class SysRoleMenu {

    @ApiModelProperty(value="角色ID",name="roleId",example="1")
    private Long roleId;

    @ApiModelProperty(value="菜单ID",name="menuId",example="1")
    private Long menuId;

}
