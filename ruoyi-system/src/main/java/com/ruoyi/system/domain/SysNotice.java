package com.ruoyi.system.domain;

import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告表 sys_notice
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="通知公告",parent=BaseEntity.class)
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="公告ID",name="noticeId",example="1")
    private Long noticeId;

    @ApiModelProperty(value="公告标题",name="noticeTitle",example="温馨提醒：2018-07-01 若依新版本发布啦")
    private String noticeTitle;

    @ApiModelProperty(value="公告类型",name="noticeType",example="1",allowableValues = "1,2",reference="1=通知,2=公告")
    private String noticeType;

    @ApiModelProperty(value="公告内容",name="noticeContent",example="新版本内容")
    private String noticeContent;

    @ApiModelProperty(value="公告状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=关闭")
    private String status;
}
