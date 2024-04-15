package com.tadpole.cloud.operationManagement.api.brand.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
* 品牌商标注册表
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@Data
@TableName("TB_BRAND_TRADEMARK_REGIS")
@ExcelIgnoreUnannotated
public class TbBrandTrademarkRegis implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌商标表主键 */
    @TableField("BT_ID")
    private Long btId;

    /** 注册国家--取值于字典名称-【国家】 */
    @TableField("REGISTER_COUNTRY")
    private String registerCountry;

    /** 商标大类--取值于字典名称-【商标大类】 */
    @TableField("TRADEMARK_CATEGORY")
    private String trademarkCategory;

    /** 商标小类 */
    @TableField("TRADEMARK_SUB_CLASS")
    private String trademarkSubClass;

    /** 公司名称--【选择】【公司管理】-【公司名称】 */
    @TableField("COMPANY_NAME")
    private String companyName;

    /** 公司地址--【选择】【公司管理】-【公司地址】 */
    @TableField("COMPANY_ADDRESS")
    private String companyAddress;

    /** 法人（中文）--【选择】【公司管理】-【法人（中文）】 */
    @TableField("LEGAL_PERSON_CHINESE")
    private String legalPersonChinese;

    /** 法人（英文）--【选择】【公司管理】-【法人（英文）】 */
    @TableField("LEGAL_PERSON_ENGLISH")
    private String legalPersonEnglish;

    /** 代理机构-编码 */
    @TableField("AGENCY_ORG_CODE")
    private String agencyOrgCode;

    /** 代理机构--【选择】【erp服务商】-【服务商】 */
    @TableField("AGENCY_ORGANIZATION")
    private String agencyOrganization;

    /** 证据提供方式--选项｛公司，代理机构｝ */
    @TableField("EVIDENCE_PROVISION_METHOD")
    private String evidenceProvisionMethod;

    /** 细类目筛选表 */
    @TableField("SUB_CATEGORY_SCREENING_TABLE")
    private String subCategoryScreeningTable;

    /** 销售链接 */
    @TableField("SALES_LINK")
    private String salesLink;

    /** 使用产品证据图--支持的图片格式{jpg;png,gif} */
    @TableField("USE_PRODUCT_EVIDENCE_DIAGRAM")
    private String useProductEvidenceDiagram;

    /** 销售订单截图--支持的图片格式{jpg;png,gif} */
    @TableField("SCREENSHOT_OF_SALES_ORDER")
    private String screenshotOfSalesOrder;

    /** 销售页面截图--支持的图片格式{jpg;png,gif} */
    @TableField("SCREENSHOTOF_SALES_PAGE")
    private String screenshotofSalesPage;

    /** 备注说明--扩类 */
    @TableField("REMARK")
    private String remark;

    /** 创建人编号 */
    @TableField("PER_CODE")
    private String perCode;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_NAME")
    private String createName;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_NAME")
    private String updateName;

}
