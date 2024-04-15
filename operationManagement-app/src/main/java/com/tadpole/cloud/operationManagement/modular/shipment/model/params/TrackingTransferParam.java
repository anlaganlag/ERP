package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 追踪明细项-调拨单
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TRACKING_TRANSFER")
public class TrackingTransferParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;

    /** 申请批次号;D6维度申请批次号 */
    @ApiModelProperty("申请批次号;D6维度申请批次号")
    private String applyBatchNo;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 店铺名称 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** SKU标签类型 */
    @ApiModelProperty("SKU标签类型")
    private String sysLabelType;

    /** 账号 */
    @ApiModelProperty("账号-店铺简称")
    private String sysShopsName;

    /** ShipmentID */
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    /** 调入仓库;接收仓库 */
    @ApiModelProperty("调入仓库;接收仓库")
    private String receiveWarehouse;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    private BigDecimal applySendQty;

    /** 发货方式;运输方式 */
    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;

    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;

    /** 申请日期 */
    @ApiModelProperty("申请日期")
    private Date applyDate;

    /** 申请追踪状态;值域{"待审核"/"已通过"/"未通过"/"同步中"/"拣货中"/"装箱中"/"物流中"/"接收中"/"已完结"/"已撤销"} */
    @ApiModelProperty("申请追踪状态;值域{0待审核-1已通过-2未通过-3待拣货-4拣货中-5装箱中-6物流中-7接收中-8已完结-9已撤销}")
    private Integer applyTrackStatus;

    /** 申请人 */
    @ApiModelProperty("申请人")
    private String apllyPerson;

    /** 申请人编号 */
    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;

    /** 审核状态;数据初始化为0--审核通过1--不通过2 */
    @ApiModelProperty("审核状态;数据初始化为0--审核通过1--不通过2")
    private Integer checkStatus;

    /** 审核原因 */
    @ApiModelProperty("审核原因")
    private String checkReason;

    /** 审核日期 */
    @ApiModelProperty("审核日期")
    private Date checkDate;

    /** 审核人编号 */
    @ApiModelProperty("审核人编号")
    private String checkPersonNo;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String checkPerson;

    /** 调拨单号;同步到k3的调拨单号 */
    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    private String billNo;

    /** 拣货完成日期 */
    @ApiModelProperty("拣货完成日期")
    private Date pickFinishDate;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 拣货数量 */
    @ApiModelProperty("拣货数量")
    private BigDecimal pickQty;

    /** 返仓数量 */
    @ApiModelProperty("返仓数量")
    private BigDecimal returnQty;

    /** 拣货单号 */
    @ApiModelProperty("拣货单号")
    private String pickListCode;

    /** 实际发货数量 */
    @ApiModelProperty("实际发货数量")
    private BigDecimal actualSendQty;

    /** 撤销人 */
    @ApiModelProperty("撤销人")
    private String revokePerson;

    /** 撤销人编号 */
    @ApiModelProperty("撤销人编号")
    private String revokePersonNo;

    /** 撤销日期 */
    @ApiModelProperty("撤销日期")
    private Date revokeDate;

    /** 撤销原因 */
    @ApiModelProperty("撤销原因")
    private String revokeReason;

    /** ERP撤销响应结果 */
    @ApiModelProperty("ERP撤销响应结果")
    private String revokeErpResult;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
