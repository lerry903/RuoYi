package com.ruoyi.system.domain;

import com.ruoyi.common.base.BaseEntity;
import com.ruoyi.common.enums.OnlineStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 当前在线会话 sys_user_online
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="当前在线会话",parent=BaseEntity.class)
public class SysUserOnline extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="用户会话id",name="sessionId",example="5c0f0dfb-2514-4b58-8795-333f509f59ed")
    private String sessionId;

    @ApiModelProperty(value="部门名称",name="deptName",example="研发部门")
    private String deptName;

    @ApiModelProperty(value="登录名称",name="loginName",example="admin")
    private String loginName;

    @ApiModelProperty(value="登录IP地址",name="ipaddr",example="127.0.0.1")
    private String ipaddr;

    @ApiModelProperty(value="登录地址",name="loginLocation",example="内网IP")
    private String loginLocation;

    @ApiModelProperty(value="浏览器类型",name="browser",example="Chrome")
    private String browser;

    @ApiModelProperty(value="操作系统",name="os",example="Windows 10")
    private String os;

    @ApiModelProperty(value="session创建时间",name="userId",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date startTimestamp;

    @ApiModelProperty(value="session最后访问时间",name="lastAccessTime",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date lastAccessTime;

    @ApiModelProperty(value="超时时间(分钟)",name="expireTime",example="1800000")
    private Long expireTime;

    @ApiModelProperty(value="在线状态",name="status",example="ON_LINE")
    private OnlineStatus status = OnlineStatus.ON_LINE;
}
