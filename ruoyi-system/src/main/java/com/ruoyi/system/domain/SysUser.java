package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="用户信息",parent=BaseEntity.class)
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "用户序号", prompt = "用户编号")
    @ApiModelProperty(value="用户序号",name="userId",example="1")
    private Long userId;

    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    @ApiModelProperty(value="部门ID",name="deptId",example="1")
    private Long deptId;

    @ApiModelProperty(value="部门父ID",name="parentId",example="1")
    private Long parentId;

    @ApiModelProperty(value="角色ID",name="roleId",example="1")
    private Long roleId;

    @Excel(name = "登录名称")
    @ApiModelProperty(value="登录名称",name="loginName",example="admin")
    private String loginName;

    @Excel(name = "用户名称")
    @ApiModelProperty(value="用户名称",name="userName",example="系统管理员")
    private String userName;

    @Excel(name = "用户邮箱")
    @ApiModelProperty(value="用户邮箱",name="email",example="1@qq.com")
    private String email;

    @Excel(name = "手机号码")
    @ApiModelProperty(value="手机号码",name="phonenumber",example="15888888888")
    private String phonenumber;

    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(value="用户性别",name="sex",example="0",allowableValues = "0,1,2",reference="0=男,1=女,2=未知")
    private String sex;

    @ApiModelProperty(value="用户头像",name="avatar")
    private String avatar;

    @ApiModelProperty(value="密码",name="password",example="123456")
    private String password;

    @ApiModelProperty(value="盐加密",name="salt",example="111111")
    private String salt;

    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value="帐号状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=停用")
    private String status;

    @ApiModelProperty(value="删除标志",name="delFlag",example="0=正常,2=删除")
    private String delFlag;

    @Excel(name = "最后登陆IP", type = Excel.Type.EXPORT)
    @ApiModelProperty(value="最后登陆IP",name="loginIp",example="127.0.0.1")
    private String loginIp;

    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty(value="最后登陆时间",name="loginDate",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date loginDate;

    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    @ApiModelProperty(value = "部门信息",hidden = true)
    private SysDept dept;

    @ApiModelProperty(value = "角色组",hidden = true)
    private List<SysRole> roles;

    @ApiModelProperty(value = "角色组",hidden = true)
    private Long[] roleIds;

    @ApiModelProperty(value = "岗位组",hidden = true)
    private Long[] postIds;

    @ApiIgnore
    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    @ApiIgnore
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public SysDept getDept() {
        return dept == null?new SysDept() : dept;
    }
}
