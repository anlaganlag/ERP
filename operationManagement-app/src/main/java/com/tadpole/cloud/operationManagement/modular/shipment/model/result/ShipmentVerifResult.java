package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@ExcelIgnoreUnannotated
@TableName("SHIPMENT_APPLY_ITEM")
public class ShipmentVerifResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("批次号")
    private String batchNo;


    @ApiModelProperty("D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("申请日期")
    private Date applyDate;






    @ApiModelProperty("交货地点编号;交货地点编号")
    private String deliverypointNo;


    @ApiModelProperty("交货地点;默认联泰发货，还可以供应商发货")
    private String deliverypoint;


    @ApiModelProperty("发货推荐数据快照ID;只有提交的数据才会触发保存推荐数据快照")
    private BigDecimal recomSnapshotId;

    @ApiModelProperty("组织编码 Amazon_账号_站点_仓库 对应的组织编号")
    private String  orgCode;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("区域")
    private String area;


    @ApiModelProperty("部门")
    private String department;


    @ApiModelProperty("Team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("物料信息;物料信息json字符串")
    private String materialInfo;


    @ApiModelProperty("产品名称")
    private String productName;


    @ApiModelProperty("ASIN")
    private String asin;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("店铺名称")
    private String shopName;


    @ApiModelProperty("站点")
    private String sysSite;


    @ApiModelProperty("SKU")
    private String sku;


    @ApiModelProperty("SKU标签类型")
    private String sysLabelType;


    @ApiModelProperty("账号-店铺简称")
    private String sysShopsName;


    @ApiModelProperty("FBA号")
    private String fbaNo;


    @ApiModelProperty("调入仓库;接收仓库")
    private String receiveWarehouse;

    /** 国内仓可调拨数 --erp系统team下改物料可调拨数量*/
    @ApiModelProperty("国内仓可调拨数")
    private BigDecimal erpCanTransferQty;

    /** 已被占用库存 --申请正在erp执行中的数量*/
    @ApiModelProperty("已被占用库存")
    private BigDecimal occupyQty;

    /** 推荐国内发货数量 */
    @ApiModelProperty("推荐国内发货数量")
    private BigDecimal domesticRecommSendQty;


    @ApiModelProperty("发货数量")
    private BigDecimal sendQty;


    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;


    @ApiModelProperty("UNW类型")
    private String unwType;


    @ApiModelProperty("备注1")
    private String remark1;


    @ApiModelProperty("备注2")
    private String remark2;


    @ApiModelProperty("备注3")
    private String remark3;


    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;


    @ApiModelProperty("库存校验;按批次校验")
    private String stockCheck;


    @ApiModelProperty("合理性判断;按批次分析")
    private String reasonableCheck;

    /** 发货前周转天数 */
    @ApiModelProperty("发货前周转天数")
    private BigDecimal turnoverBeforeSendDays;

    @ApiModelProperty("按批次分析发货后周转天数")
    private BigDecimal turnoverAfterSendDays;




    @ApiModelProperty("申请状态;数据初始化为0--已提交1")
    private Integer applyStatus;


    @ApiModelProperty("申请人")
    private String apllyPerson;


    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;


    @ApiModelProperty("审核状态;数据初始化为0--审核通过1--不通过2")
    private Integer checkStatus;


    @ApiModelProperty("审核原因")
    private String checkReason;


    @ApiModelProperty("审核日期")
    private Date checkDate;


    @ApiModelProperty("审核人编号")
    private String checkPersonNo;


    @ApiModelProperty("审核人")
    private String checkPerson;


    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    private String billNo;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;


    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;


    @ApiModelProperty("同步结果")
    private String syncResultMsg;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    /**
     * 调入仓库code;接收仓库code
     */
    @ApiModelProperty("调入仓库code")
    private String receiveWarehouseCode;


    List<ShipmentApplyItem> valueList =new ArrayList<>();


}
