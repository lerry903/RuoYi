package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作日志记录表 sys_oper_log
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="操作日志",parent=BaseEntity.class)
public class SysOperLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "操作序号")
    @ApiModelProperty(value="操作序号",name="operId",example="1")
    private Long operId;

    @Excel(name = "操作模块")
    @ApiModelProperty(value="操作模块",name="title",example="在线用户")
    private String title;

    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    @ApiModelProperty(value="业务类型",name="businessType",example="0",allowableValues = "range[0,9]",reference="0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    @ApiModelProperty(value="业务类型数组",name="businessTypes",example="[1,2,3]")
    private Integer[] businessTypes;

    @Excel(name = "请求方法")
    @ApiModelProperty(value="请求方法",name="method",example="com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()")
    private String method;

    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    @ApiModelProperty(value="操作类别",name="operatorType",example="0",allowableValues = "0,1,2",reference="0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    @Excel(name = "操作人员")
    @ApiModelProperty(value="操作人",name="operName",example="admin")
    private String operName;

    @Excel(name = "部门名称")
    @ApiModelProperty(value="部门名称",name="deptName",example="研发部门")
    private String deptName;

    @Excel(name = "请求地址")
    @ApiModelProperty(value="请求URL地址",name="operUrl",example="/monitor/online/forceLogout")
    private String operUrl;

    @Excel(name = "操作地址")
    @ApiModelProperty(value="操作IP地址",name="operIp",example="127.0.0.1")
    private String operIp;

    @Excel(name = "操作地点")
    @ApiModelProperty(value="操作地点",name="operLocation",example="内网IP")
    private String operLocation;

    @Excel(name = "请求参数")
    @ApiModelProperty(value="请求参数",name="operParam")
    private String operParam;

    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    @ApiModelProperty(value="操作状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=异常")
    private Integer status;

    @Excel(name = "错误消息")
    @ApiModelProperty(value="错误消息",name="errorMsg")
    private String errorMsg;

    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="操作时间",name="operTime",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date operTime;
}
