package com.ruoyi.quartz.domain;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.quartz.util.CronUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * 定时任务调度表 sys_job
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="定时任务调度",parent=BaseEntity.class)
public class SysJob extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Excel(name = "任务序号")
    @ApiModelProperty(value="任务ID",name="jobId",example="1")
    private Long jobId;

    @Excel(name = "任务名称")
    @ApiModelProperty(value="任务名称",name="jobName",example="ryTask")
    private String jobName;

    @Excel(name = "任务组名")
    @ApiModelProperty(value="任务组名",name="jobGroup",example = "系统默认（无参）")
    private String jobGroup;

    @Excel(name = "任务方法")
    @ApiModelProperty(value="任务方法",name="methodName",example="ryNoParams")
    private String methodName;

    @Excel(name = "方法参数")
    @ApiModelProperty(value="方法参数",name="methodParams")
    private String methodParams;

    @Excel(name = "执行表达式 ")
    @ApiModelProperty(value="cron执行表达式",name="cronExpression",example="0/10 * * * * ?")
    private String cronExpression;

    @Excel(name = "计划策略 ")
    @ApiModelProperty(value="cron计划策略",name="misfirePolicy",example="0")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    @Excel(name = "是否并发执行" , readConverterExp = "0=允许,1=禁止")
    @ApiModelProperty(value="是否并发执行",name="status",example="0",allowableValues = "0,1",reference="0=允许,1=禁止")
    private String concurrent;

    @Excel(name = "任务状态" , readConverterExp = "0=正常,1=暂停")
    @ApiModelProperty(value="任务状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=暂停")
    private String status;

    /**
     * 下次执行时间
     * @return 下次执行时间
     */
    @ApiIgnore(value = "下次执行时间")
    public Date getNextValidTime(){
        if (StrUtil.isNotEmpty(cronExpression)){
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }
}
