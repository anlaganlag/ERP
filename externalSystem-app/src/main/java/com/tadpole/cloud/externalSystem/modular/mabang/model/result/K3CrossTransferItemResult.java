package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("K3_CROSS_TRANSFER_ITEM")
public class K3CrossTransferItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** K3跨组织直接调拨单对应数据id */
    @ApiModelProperty("CROSS_ID K3跨组织直接调拨单对应数据id")
    private BigDecimal crossId;

    /** 产品类型|默认值：Standard标准产品	枚举类型：Standard 标准产品，Parent 套件父项，Son 套件子项， Service 服务 */
    @ApiModelProperty("F_ROW_TYPE")
    private String fRowType;

    /** SKU| */
    @ApiModelProperty(name="fbscBase",value="平台SKU")
    private String fBscBase;

    /** 物料编码| */
    @ApiModelProperty(name="fmaterialId",value="物料编码")
    private String fMaterialId;

    /** 物料名称|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @ApiModelProperty("F_MATERIAL_NAME物料名称")
    private String fMaterialName;

    /** 规格型号|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @ApiModelProperty("F_MODEL")
    private String fModel;

    /** 单位|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @ApiModelProperty("F_UNIT_ID单位")
    private String fUnitId;

    /** 调拨数量| */
    @ApiModelProperty("F_QTY调拨数量")
    private Long fQty;

    /** 调出仓库| */
    @ApiModelProperty("F_SRC_STOCK_ID")
    private String fSrcStockId;

    /** 调入仓库|用马帮订单接口中shopId关联出财务编码，通过这个编码为K3【仓库列表】中使用组织为该财务编码的仓库编码，找出仓库Id字段填入 */
    @ApiModelProperty("F_DEST_STOCK_ID")
    private String fDestStockId;

    /** 调出库存状态|默认值：KCZT01_SYS可用 */
    @ApiModelProperty("F_SRC_STOCK_STATUS_ID")
    private String fSrcStockStatusId;

    /** 调入库存状态|默认值：KCZT01_SYS可用 */
    @ApiModelProperty("F_DEST_STOCK_STATUS_ID")
    private String fDestStockStatusId;

    /** 调出货主|默认值：同单头字段 */
    @ApiModelProperty("F_OWNER_OUT_ID")
    private String fOwnerOutId;

    /** 调入货主|默认值：同单头字段 */
    @ApiModelProperty("F_OWNER_ID")
    private String fOwnerId;

    /** 需求Team|默认值：平台发展组 */
    @ApiModelProperty("F_BSC_TEAM")
    private String fBscTeam;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @ApiModelProperty("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 马帮仓库名称 */
    @ApiModelProperty("STOCK_WAREHOUSE马帮仓库名称")
    private String stockWarehouse;

    /** 马帮仓库编号 */
    @ApiModelProperty("STOCK_WAREHOUSE_ID马帮仓库编号")
    private String stockWarehouseId;

    @ApiModelProperty("F_Bill_NO K3直接调拨单号")
    private String fBillNo;

    @ApiModelProperty("F_NOTE 平台订单编号")
    private String fNote;

    @ApiModelProperty("PLATFORM_NAME平台名称")
    private String platformName;

    @ApiModelProperty("SHOP_NAME店铺名称")
    private String shopName;

    /** BIZ_TYPE 跨组织调拨单业务类型（YFHDD:已发货订单,XSTHDD销售退货订单） */
    @ApiModelProperty("跨组织调拨单业务类型（YFHDD:已发货订单,XSTHDD销售退货订单）")
    private String bizType;
}