package com.tadpole.cloud.operationManagement.api.brand.model.params;

import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
* <p>
* 品牌商标注册表
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbBrandTrademarkRegisParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌商标表主键")
    //@NotNull
    private Long btId;

    @ApiModelProperty("注册国家--取值于字典名称-【国家】")
    //@NotNull(message = "注册国家不能为空")
    private String registerCountry;

    @ApiModelProperty("商标大类--取值于字典名称-【商标大类】")
    //@NotNull(message = "商标大类不能为空")
    private String trademarkCategory;

    @ApiModelProperty("商标小类")
    private String trademarkSubClass;

    @ApiModelProperty("公司名称--【选择】【公司管理】-【公司名称】")
    private String companyName;

    @ApiModelProperty("公司地址--【选择】【公司管理】-【公司地址】")
    private String companyAddress;

    @ApiModelProperty("法人（中文）--【选择】【公司管理】-【法人（中文）】")
    private String legalPersonChinese;

    @ApiModelProperty("法人（英文）--【选择】【公司管理】-【法人（英文）】")
    private String legalPersonEnglish;

    @ApiModelProperty("代理机构-编码")
    private String agencyOrgCode;

    @ApiModelProperty("代理机构--【选择】【erp服务商】-【服务商】")
    private String agencyOrganization;

    @ApiModelProperty("证据提供方式--选项｛公司，代理机构｝")
    private String evidenceProvisionMethod;

    @ApiModelProperty("细类目筛选表")
    private String subCategoryScreeningTable;

    @ApiModelProperty("销售链接")
    private String salesLink;

    @ApiModelProperty("使用产品证据图--支持的图片格式{jpg;png,gif}")
    private String useProductEvidenceDiagram;

    @ApiModelProperty("销售订单截图--支持的图片格式{jpg;png,gif}")
    private String screenshotOfSalesOrder;

    @ApiModelProperty("销售页面截图--支持的图片格式{jpg;png,gif}")
    private String screenshotofSalesPage;

    @ApiModelProperty("备注说明--扩类")
    private String remark;

    @ApiModelProperty("创建人编号")
    private String perCode;

    @ApiModelProperty("")
    private Date createTime;

    @ApiModelProperty("")
    private String createName;

    @ApiModelProperty("")
    private Date updateTime;

    @ApiModelProperty("")
    private String updateName;

}
