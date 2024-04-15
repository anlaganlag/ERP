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
    * K3跨组织直接调拨单明细项
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_CROSS_TRANSFER_ITEM")
@ExcelIgnoreUnannotated
public class K3CrossTransferItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** K3跨组织直接调拨单对应数据id */
    @TableField("CROSS_ID")
    private BigDecimal crossId;

    /** 产品类型|默认值：Standard标准产品	枚举类型：Standard 标准产品，Parent 套件父项，Son 套件子项， Service 服务 */
    @TableField("F_ROW_TYPE")
    private String fRowType;

    /** SKU| */
    @TableField("F_BSC_BASE")
    private String fBscBase;

    /** 物料编码| */
    @TableField("F_MATERIAL_ID")
    private String fMaterialId;

    /** 物料名称|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @TableField("F_MATERIAL_NAME")
    private String fMaterialName;

    /** 规格型号|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @TableField("F_MODEL")
    private String fModel;

    /** 单位|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @TableField("F_UNIT_ID")
    private String fUnitId;

    /** 调拨数量| */
    @TableField("F_QTY")
    private BigDecimal fQty;

    /** 调出仓库| */
    @TableField("F_SRC_STOCK_ID")
    private String fSrcStockId;

    /** 调入仓库|用马帮订单接口中shopId关联出财务编码，通过这个编码为K3【仓库列表】中使用组织为该财务编码的仓库编码，找出仓库Id字段填入 */
    @TableField("F_DEST_STOCK_ID")
    private String fDestStockId;

    /** 调出库存状态|默认值：KCZT01_SYS可用 */
    @TableField("F_SRC_STOCK_STATUS_ID")
    private String fSrcStockStatusId;

    /** 调入库存状态|默认值：KCZT01_SYS可用 */
    @TableField("F_DEST_STOCK_STATUS_ID")
    private String fDestStockStatusId;

    /** 调出货主|默认值：同单头字段 */
    @TableField("F_OWNER_OUT_ID")
    private String fOwnerOutId;

    /** 调入货主|默认值：同单头字段 */
    @TableField("F_OWNER_ID")
    private String fOwnerId;

    /** 需求Team|默认值：平台发展组 */
    @TableField("F_BSC_TEAM")
    private String fBscTeam;

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
    @TableField("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 马帮仓库名称 */
    @TableField("STOCK_WAREHOUSE")
    private String stockWarehouse;

    /** 马帮仓库编号 */
    @TableField("STOCK_WAREHOUSE_ID")
    private String stockWarehouseId;

    /** BIZ_TYPE 跨组织调拨单业务类型（YFHDD:已发货订单,XSTHDD销售退货订单） */
    @TableField("BIZ_TYPE")
    private String bizType;

}