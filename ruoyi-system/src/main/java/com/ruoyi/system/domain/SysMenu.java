package com.ruoyi.system.domain;

import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 sys_menu
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="菜单权限",parent=BaseEntity.class)
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="菜单ID",name="menuId",example="100")
    private Long menuId;

    @ApiModelProperty(value="菜单名称",name="menuName",example="用户管理")
    private String menuName;

    @ApiModelProperty(value="父菜单名称",name="parentName",example="系统管理")
    private String parentName;

    @ApiModelProperty(value="父菜单ID",name="parentId",example="1")
    private Long parentId;

    @ApiModelProperty(value="显示顺序",name="orderNum",example="1")
    private String orderNum;

    @ApiModelProperty(value="请求地址",name="url",example="/system/user")
    private String url;

    @ApiModelProperty(value="菜单打开方式",name="target",example="menuItem",reference = "menuItem页签 menuBlank新窗口")
    private String target;

    @ApiModelProperty(value="菜单类型",name="menuType",example="C",allowableValues = "M,C,F",reference="M=目录,C=菜单,F=按钮")
    private String menuType;

    @ApiModelProperty(value="菜单状态",name="visible",example="0",allowableValues = "0,1",reference="0=显示,1=隐藏")
    private String visible;

    @ApiModelProperty(value="权限标识",name="perms",example="system:user:view")
    private String perms;

    @ApiModelProperty(value="菜单图标",name="icon",example="#")
    private String icon;

    @ApiModelProperty(value="子菜单",name="children",hidden = true)
    private List<SysMenu> children = new ArrayList<>();
}
