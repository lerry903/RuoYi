package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="字典类型",parent=BaseEntity.class)
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "字典主键")
    @ApiModelProperty(value="字典主键",name="dictId",example="1")
    private Long dictId;

    @Excel(name = "字典名称")
    @ApiModelProperty(value="字典名称",name="dictName",example="用户性别")
    private String dictName;

    @Excel(name = "字典类型 ")
    @ApiModelProperty(value="字典类型",name="dictType",example="sys_user_sex")
    private String dictType;

    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value="状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=停用")
    private String status;

}
