package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="参数配置",parent=BaseEntity.class)
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "参数主键")
    @ApiModelProperty(value="参数主键",name="configId",example="1")
    private Long configId;

    @Excel(name = "参数名称")
    @ApiModelProperty(value="参数名称",name="configName",example="主框架页-默认皮肤样式名称")
    private String configName;

    @Excel(name = "参数键名")
    @ApiModelProperty(value="参数键名",name="configKey",example="sys.index.skinName")
    private String configKey;

    @Excel(name = "参数键值")
    @ApiModelProperty(value="参数键值",name="configValue",example="skin-default")
    private String configValue;

    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    @ApiModelProperty(value="系统内置",name="configType",example="Y",allowableValues = "Y,N",reference="Y=是,N=否")
    private String configType;
}
