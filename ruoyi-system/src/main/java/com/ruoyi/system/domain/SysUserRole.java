package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author ruoyi
 */
@Data
@ApiModel(description="用户和角色关联关系")
public class SysUserRole implements Serializable {

    @ApiModelProperty(value="用户ID",name="userId",example="1")
    private Long userId;

    @ApiModelProperty(value="角色ID",name="roleId",example="1")
    private Long roleId;
}
