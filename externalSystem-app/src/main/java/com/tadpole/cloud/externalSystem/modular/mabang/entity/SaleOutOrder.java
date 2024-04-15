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
    * 销售出库单
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SALE_OUT_ORDER")
@ExcelIgnoreUnannotated
public class SaleOutOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** K3销售出库单号,接口传递给k3 */
    @TableField("BILL_NO")
    private String billNo;

    /** 年份 */
    @TableField("YEARS")
    private String years;

    /** 月份 */
    @TableField("MONTH")
    private String month;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @TableField("SAL_ORG_NAME")
    private String salOrgName;

    /** 销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @TableField("SAL_ORG_CODE")
    private String salOrgCode;

    /** 物料编码:即库存SKU */
    @TableField("STOCK_SKU")
    private String stockSku;

    /** 销售出库数量累加 */
    @TableField("OUT_QTY_SUM")
    private BigDecimal outQtySum;

    /** 调用k3接口传递字段FBillTypeID--默认值：XSCKD01_SYS	默认值对应显示名称：标准销售出库单 */
    @TableField("F_BILL_TYPE_ID")
    private String fBillTypeId;

    /** 调用k3接口传递字段FBillNo */
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 调用k3接口传递字段FDate--年份+月份+1日 */
    @TableField("F_DATE")
    private String fDate;

    /** 调用k3接口传递字段FSaleOrgId--销售组织编码 */
    @TableField("F_SALE_ORG_ID")
    private String fSaleOrgId;

    /** 调用k3接口传递字段FCustomerID--默认值：店铺虚拟客户 */
    @TableField("F_CUSTOMER_ID")
    private String fCustomerId;

    /** 调用k3接口传递字段F_BSC_type--枚举类型，默认值：0	默认值对应显示名称：低价销售在库产品(外） */
    @TableField("F_BSC_TYPE")
    private String fBscType;

    /** 调用k3接口传递字段FStockOrgId--库存组织编码 */
    @TableField("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 调用k3接口传递字段FNote--默认值：id */
    @TableField("F_NOTE")
    private String fNote;

    /** 调用k3接口传递字段FSettleCurrID--默认值：PRE001	默认值对应显示名称：人民币 */
    @TableField("F_SETTLE_CURR_ID")
    private String fSettleCurrId;

    /** 调用k3接口传递字段FDocumentStatus--金蝶ERP单据上的系统字段 ：默认值：已审核 */
    @TableField("F_DOCUMENT_STATUS")
    private String fDocumentStatus;

    /** 是否作废Y/N，默认N（代表所有item是否作废） */
    @TableField("IS_INVALID")
    private String isInvalid;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（-1：数据初始化，0 ：同步失败,1：同步成功） */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 最后一次同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}