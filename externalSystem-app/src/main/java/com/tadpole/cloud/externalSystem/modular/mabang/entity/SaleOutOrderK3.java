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
    * 根据K3仓库可用数量自动产生-销售出库单
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SALE_OUT_ORDER_K3")
@ExcelIgnoreUnannotated
public class SaleOutOrderK3 implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id--数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 调用k3接口传递字段FBillNo--调用k3接口传递字段FBillNo */
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 调用k3接口传递字段FDate--年份+月份+1日--调用k3接口传递字段FDate--年份+月份+1日 */
    @TableField("F_DATE")
    private String fDate;

    /** F_CREATOR_ID-- */
    @TableField("F_CREATOR_ID")
    private String fCreatorId;

    /** 调用k3接口传递字段FSaleOrgId--销售组织编码--销售组织编码 */
    @TableField("F_SALE_ORG_ID")
    private String fSaleOrgId;

    /** 调用k3接口传递字段FCustomerID--默认值：店铺虚拟客户--默认值：店铺虚拟客户 */
    @TableField("F_CUSTOMER_ID")
    private String fCustomerId;

    /** F_CORRESPOND_ORG_ID-- */
    @TableField("F_CORRESPOND_ORG_ID")
    private String fCorrespondOrgId;

    /** F_PAYER_ID-- */
    @TableField("F_PAYER_ID")
    private String fPayerId;

    /** 库存组织编码--调用k3接口传递字段FStockOrgId--库存组织编码 */
    @TableField("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 备注--调用k3接口传递字段FNote--默认值：id */
    @TableField("F_NOTE")
    private String fNote;

    /** 是否创建了马帮采购单--默认值：-1数据初始化，0生成采购单失败，1生成采购单全部成功 */
    @TableField("CREATE_PURCHASE_ORDER")
    private BigDecimal createPurchaseOrder;

    /** 是否删除--是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式--同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间--同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态--同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步请求参数--同步请求参数 */
    @TableField("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容--同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间--创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间--更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}