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
    * 发货申请明细项
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
@TableName("SHIPMENT_APPLY_ITEM")
public class ShipmentApplyItemParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;

    /** 批次号;D6维度申请批次号 */
    @ApiModelProperty("批次号")
    private String batchNo;

    /** 申请批次号 */
    @ApiModelProperty("D6维度申请批次号")
    private String applyBatchNo;

    /** 交货地点编号;交货地点编号 */
    @ApiModelProperty("交货地点编号;交货地点编号")
    private String deliverypointNo;

    /** 交货地点;默认联泰发货，还可以供应商发货 */
    @ApiModelProperty("交货地点;默认联泰发货，还可以供应商发货")
    private String deliverypoint;

    /** 发货推荐数据快照ID;只有提交的数据才会触发保存推荐数据快照 */
    @ApiModelProperty("发货推荐数据快照ID;只有提交的数据才会触发保存推荐数据快照")
    private BigDecimal recomSnapshotId;


    /**
     * 组织编码   Amazon_账号_站点_仓库 对应的组织编号
     */
    @ApiModelProperty("组织编码 Amazon_账号_站点_仓库 对应的组织编号")
    private String  orgCode;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    /** 部门 */
    @ApiModelProperty("部门")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料信息;物料信息json字符串 */
    @ApiModelProperty("物料信息;物料信息json字符串")
    private String materialInfo;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

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

    /** FBA号 */
    @ApiModelProperty("FBA号")
    private String fbaNo;

    /** 调入仓库;接收仓库 */
    @ApiModelProperty("调入仓库;接收仓库")
    private String receiveWarehouse;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    private BigDecimal sendQty;

    /** 发货方式;运输方式 */
    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;

    /** UNW类型 */
    @ApiModelProperty("UNW类型")
    private String unwType;

    /** 备注1 */
    @ApiModelProperty("备注1")
    private String remark1;

    /** 备注2 */
    @ApiModelProperty("备注2")
    private String remark2;

    /** 备注3 */
    @ApiModelProperty("备注3")
    private String remark3;

    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;

    /** 库存校验;按批次校验 */
    @ApiModelProperty("库存校验;按批次校验")
    private String stockCheck;

    /** 合理性判断;按批次分析 */
    @ApiModelProperty("合理性判断;按批次分析")
    private String reasonableCheck;

    /** 按批次分析发货后周转天数*/
    @ApiModelProperty("按批次分析发货后周转天数")
    private BigDecimal turnoverAfterSendDays;

    /** 申请日期 */
    @ApiModelProperty("申请日期")
    private Date applyDate;

    /** 申请状态;数据初始化为0--已提交1 */
    @ApiModelProperty("申请状态;数据初始化为0--已提交1")
    private Integer applyStatus;

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

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步K3状态;数据初始化为0-调用K3接口成功1--失败2 */
    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;

    /** 同步请求参数 */
    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;

    /** 同步结果 */
    @ApiModelProperty("同步结果")
    private String syncResultMsg;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

    /**
     * 调入仓库code;接收仓库code
     */
    @ApiModelProperty("调入仓库code")
    private String receiveWarehouseCode;

}
