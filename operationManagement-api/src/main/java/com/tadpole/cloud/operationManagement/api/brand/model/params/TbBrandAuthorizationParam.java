package com.tadpole.cloud.operationManagement.api.brand.model.params;

import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
* <p>
* 品牌授权表
* </p>
* @author S20190161
* @since 2023-10-30
*/
@Data
public class TbBrandAuthorizationParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌主表主键")
    private Long bcId;

    @ApiModelProperty("注册号")
    private String registrationNo;

    @ApiModelProperty("注册国家")
    private String registeredCountry;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("br注册店铺")
    private String registeredStore;

    @ApiModelProperty("br注册邮箱")
    private String registeredMail;

    @ApiModelProperty("br授权店铺")
    private String authorizedStore;

    @ApiModelProperty("br授权邮箱")
    private String authorizedMail;

    @ApiModelProperty("br授权角色")
    private String authorizedRole;

    @ApiModelProperty("创建人编号")
    private String sysPerCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人姓名")
    private String createName;

    @ApiModelProperty("")
    private Date updateTime;

    @ApiModelProperty("")
    private String updateName;

}
