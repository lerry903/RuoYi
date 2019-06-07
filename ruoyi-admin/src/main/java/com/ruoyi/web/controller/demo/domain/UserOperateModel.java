package com.ruoyi.web.controller.demo.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.base.BaseEntity;
import com.ruoyi.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author LErry.li
 * Description:
 * 操作用户对象
 * Date: 2019-6-7 15:47:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserOperateModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private int userId;

    @Excel(name = "用户编号")
    @ApiModelProperty(value="用户编号",name="userCode")
    private String userCode;

    @Excel(name = "用户姓名")
    @ApiModelProperty(value="用户姓名",name="userName")
    private String userName;

    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(value="用户性别",name="userSex",example="0",allowableValues = "0,1,2",reference="0=男,1=女,2=未知")
    private String userSex;

    @Excel(name = "用户手机")
    @ApiModelProperty(value="用户手机",name="userPhone")
    private String userPhone;

    @Excel(name = "用户邮箱")
    @ApiModelProperty(value="用户邮箱",name="userEmail")
    private String userEmail;

    @Excel(name = "用户余额")
    @ApiModelProperty(value="用户余额",name="userBalance")
    private double userBalance;

    @Excel(name = "用户状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value="用户状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=停用")
    private String status;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @ApiModelProperty(value="创建时间",name="createTime",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date createTime;

    public UserOperateModel() {

    }

    public UserOperateModel(int userId, String userCode, String userName, String userSex, String userPhone,
                            String userEmail, double userBalance, String status) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtil.date();
    }
}