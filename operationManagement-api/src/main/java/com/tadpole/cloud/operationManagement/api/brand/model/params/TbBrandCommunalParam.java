package com.tadpole.cloud.operationManagement.api.brand.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
* <p>
* 品牌管理主表
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbBrandCommunalParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("销售品牌名称")
    private String salesBrand;

 @ApiModelProperty("销售品牌名称")
 private List<String> salesBrands;
    @ApiModelProperty("状态：1.启用;0.禁用;默认启用")
    private Integer isEnable;

    @ApiModelProperty("售后服务邮箱")
    private String afterSalesServiceMail;

    @ApiModelProperty("绑定电话")
    private String bindingPhone;

    @ApiModelProperty("官网")
    private String officialWebsite;

    @ApiModelProperty("facebook")
    private String facebook;

    @ApiModelProperty("instagram")
    private String instagram;

    @ApiModelProperty("创建时间")
    private Date sysCreateDate;

    @ApiModelProperty("创建人编号")
    private String sysPerCode;

    @ApiModelProperty("创建人姓名")
    private String sysPerName;

}
