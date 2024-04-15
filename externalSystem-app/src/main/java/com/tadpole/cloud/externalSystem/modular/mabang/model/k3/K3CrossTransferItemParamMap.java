package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

;

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
public class K3CrossTransferItemParamMap implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** K3跨组织直接调拨单对应数据id */
    @ApiModelProperty("CROSS_ID")
    private BigDecimal crossId;

    /** 产品类型|默认值：Standard标准产品	枚举类型：Standard 标准产品，Parent 套件父项，Son 套件子项， Service 服务 */
    @ApiModelProperty("F_ROW_TYPE")
    @JSONField(name="FRowType",ordinal = 2)
    private String fRowType;

    /** SKU| */
    @JSONField(name="F_BSC_Base", serializeUsing = TransferSerializer.class,ordinal = 1)
    @ApiModelProperty("F_BSC_BASE")
    private String fBscBase;

    /** 物料编码| */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FMaterialId",ordinal = 3)
    @ApiModelProperty("F_MATERIAL_ID")
    private String fMaterialId;

    /** 物料名称|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @ApiModelProperty("F_MATERIAL_NAME")
    private String fMaterialName;

    /** 规格型号|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @ApiModelProperty("F_MODEL")
    private String fModel;

    /** 单位|根据物料编码，在K3【物料列表】作业中查询自动带出 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FUnitID",ordinal = 4)
    @ApiModelProperty("F_UNIT_ID")
    private String fUnitId;

    /** 调拨数量| */
    @ApiModelProperty("F_QTY")
    @JSONField(ordinal = 5,name = "FQty")
    private BigDecimal fQty;

    /** 调出仓库| */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FSrcStockId",ordinal = 6)
    @ApiModelProperty("F_SRC_STOCK_ID")
    private String fSrcStockId;

    /** 调入仓库|用马帮订单接口中shopId关联出财务编码，通过这个编码为K3【仓库列表】中使用组织为该财务编码的仓库编码，找出仓库Id字段填入 */
    @JSONField(name = "FDestStockId",serializeUsing =TransferSerializer.class,ordinal = 7)
    @ApiModelProperty("F_DEST_STOCK_ID")
    private String fDestStockId;

    /** 调出库存状态|默认值：KCZT01_SYS可用 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FSrcStockStatusId",ordinal = 8)
    @ApiModelProperty("F_SRC_STOCK_STATUS_ID")
    private String fSrcStockStatusId;

    /** 调入库存状态|默认值：KCZT01_SYS可用 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FDestStockStatusId",ordinal = 9)
    @ApiModelProperty("F_DEST_STOCK_STATUS_ID")
    private String fDestStockStatusId;

    /** 调出货主|默认值：同单头字段 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FOwnerOutId",ordinal = 10)
    @ApiModelProperty("F_OWNER_OUT_ID")
    private String fOwnerOutId;

    /** 调入货主|默认值：同单头字段 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FOwnerId",ordinal = 10)
    @ApiModelProperty("F_OWNER_ID")
    private String fOwnerId;

    /** 需求Team|默认值：平台发展组 */
    @JSONField(name="F_BSC_Team",serializeUsing =TransferSerializer.class,ordinal = 11)
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

    /** 备注*/
    @JSONField(ordinal = 12,name = "FNoteEntry")
    @ApiModelProperty("FNoteEntry")
    private String FNoteEntry;
}