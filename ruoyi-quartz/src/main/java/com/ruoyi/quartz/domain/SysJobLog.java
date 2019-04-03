package com.ruoyi.quartz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 定时任务调度日志表 sys_job_log
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="定时任务调度日志",parent=BaseEntity.class)
public class SysJobLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "日志序号")
    @ApiModelProperty(value="日志序号",name="jobLogId",example="1")
    private Long jobLogId;

    @Excel(name = "任务名称")
    @ApiModelProperty(value="任务名称",name="jobName",example="ryTask")
    private String jobName;

    @Excel(name = "任务组名")
    @ApiModelProperty(value="任务组名",name="jobGroup",example="系统默认（无参）")
    private String jobGroup;

    @Excel(name = "任务方法")
    @ApiModelProperty(value="任务方法",name="methodName",example="ryNoParams")
    private String methodName;

    @Excel(name = "方法参数")
    @ApiModelProperty(value="方法参数",name="methodParams")
    private String methodParams;

    @Excel(name = "日志信息")
    @ApiModelProperty(value="日志信息",name="jobMessage",example="ryTask 总共耗时：2毫秒")
    private String jobMessage;

    @Excel(name = "执行状态" , readConverterExp = "0=正常,1=失败")
    @ApiModelProperty(value="执行状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=失败")
    private String status;

    @Excel(name = "异常信息")
    @ApiModelProperty(value="异常信息",name="exceptionInfo")
    private String exceptionInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间",name="startTime",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="结束时间",name="endTime",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date endTime;
}
