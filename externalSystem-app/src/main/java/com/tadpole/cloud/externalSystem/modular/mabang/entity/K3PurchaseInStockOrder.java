package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * K3采购入库单
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_PURCHASE_IN_STOCK_ORDER")
@ExcelIgnoreUnannotated
public class K3PurchaseInStockOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 单据编号 */
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 单据类型 */
    @TableField("F_BILL_TYPE_ID")
    private String fBillTypeId;

    /** 入库日期 */
    @TableField("F_DATE")
    private Date fDate;

    /** 收料组织 */
    @TableField("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 采购组织 */
    @TableField("F_PURCHASE_ORG_ID")
    private String fPurchaseOrgId;

    /** 供应商 */
    @TableField("F_SUPPLIER_ID")
    private String fSupplierId;

    /** 货主类型 */
    @TableField("F_OWNER_TYPE_ID_HEAD")
    private String fOwnerTypeIdHead;

    /** 货主 */
    @TableField("F_OWNER_ID_HEAD")
    private String fOwnerIdHead;

    /** 结算组织 */
    @TableField("F_SETTLE_ORG_ID")
    private String fSettleOrgId;

    /** 结算币别 */
    @TableField("F_SETTLE_CURR_ID")
    private String fSettleCurrId;

    /** 定价时点 */
    @TableField("F_PRICE_TIME_POINT")
    private String fPriceTimePoint;

    /** 同步方式 */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态;同步状态(-1：初始化，0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步请求参数 */
    @TableField("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** K3采购入库单审核时间 */
    @TableField("VERIFY_DATE")
    private Date verifyDate;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

}