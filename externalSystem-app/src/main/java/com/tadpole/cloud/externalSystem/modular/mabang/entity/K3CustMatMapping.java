package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * K3客户物料对应表
    * </p>
*
* @author lsy
* @since 2022-06-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_CUST_MAT_MAPPING")
@ExcelIgnoreUnannotated
public class K3CustMatMapping implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 单据编号 */
    @TableField("SUMMARY_ID")
    private String summaryId;

    /** 财务编码 */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 客户 */
    @TableField("NAME")
    private String name;

    /** 销售组织，默认值：采购中心 */
    @TableField("SALE_ORG_ID")
    private String saleOrgId;

    /** 客户ID */
    @TableField("CUSTOMER_ID")
    private String customerId;

    /** 单据状态，默认值：创建 */
    @TableField("DOCUMENT_STATUS")
    private String documentStatus;

    /** 是否多选客户，默认值：否 */
    @TableField("IS_MULTIPLE")
    private String isMultiple;

    /** SKU */
    @TableField("CUST_MAT_NO")
    private String custMatNo;

    /** 客户物料名称 */
    @TableField("CUST_MAT_NAME")
    private String custMatName;

    /** 物料编码 */
    @TableField("MATERIAL_ID")
    private String materialId;

    /** 物料名称，由物料编码字段带出 */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /** 启用，默认值：是 */
    @TableField("EFFECTIVE")
    private String effective;

    /** FNSKU，亚马逊平台专用字段，留空 */
    @TableField("FNSKU")
    private String fnsku;

    /** 默认携带，默认值：否 */
    @TableField("DEF_CARRY")
    private String defCarry;

    /** 标签短描述，由物料编码字段带出 */
    @TableField("SHORT_DESC")
    private String shortDesc;

    /** 站点，由客户字段中截取最后一小段带出 */
    @TableField("STATION")
    private String station;

    /** 颜色，由物料编码字段带出 */
    @TableField("BSC_COLOR")
    private String bscColor;

    /** 模板名称，留空 */
    @TableField("BSC_TEMPLATE_NAME")
    private String bscTemplateName;

    /** 是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @TableField("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;


    @TableField("shop_id")
    private String shopId;


    @TableField("platform_id")
    private String platformId;

}