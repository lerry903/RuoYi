package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表 sys_dict_data
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="数据字典",parent=BaseEntity.class)
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "字典编码")
    @ApiModelProperty(value="字典编码",name="dictCode",example="1")
    private Long dictCode;

    @Excel(name = "字典排序")
    @ApiModelProperty(value="字典排序",name="dictSort",example="1")
    private Long dictSort;

    @Excel(name = "字典标签")
    @ApiModelProperty(value="字典标签",name="dictLabel",example="男")
    private String dictLabel;

    @Excel(name = "字典键值")
    @ApiModelProperty(value="字典键值",name="dictValue",example="0")
    private String dictValue;

    @Excel(name = "字典类型")
    @ApiModelProperty(value="字典类型",name="dictType",example="sys_user_sex")
    private String dictType;

    @Excel(name = "字典样式")
    @ApiModelProperty(value="字典样式",name="cssClass")
    private String cssClass;

    @ApiModelProperty(value="表格字典样式",name="listClass")
    private String listClass;

    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    @ApiModelProperty(value="是否默认",name="isDefault",example="N",allowableValues = "Y,N",reference="Y=是,N=否")
    private String isDefault;

    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value="状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=停用")
    private String status;
}
