package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 发货追踪汇总表
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
@TableName("SHIPMENT_TRACKING")
public class ShipmentTrackingResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("发货申请批次号;D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;


    @ApiModelProperty("申请日期")
    private Date applyDate;


    @ApiModelProperty("申请人")
    private String apllyPerson;


    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;


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


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("产品名称")
    private String productName;


    @ApiModelProperty("ASIN")
    private String asin;


    @ApiModelProperty("发货数量")
    private BigDecimal sendQty;


    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;


    @ApiModelProperty("申请状态;数据初始化为0--已提交1")
    private Integer applyStatus;


    @ApiModelProperty("审核状态;数据初始化为待审核0--审核通过1--不通过2")
    private Integer checkStatus;

    @ApiModelProperty("审核状态名称")
    private String checkStatusName;



    @ApiModelProperty("审核原因")
    private String checkReason;


    @ApiModelProperty("审核日期")
    private Date checkDate;


    @ApiModelProperty("审核人编号")
    private String checkPersonNo;


    @ApiModelProperty("审核人")
    private String checkPerson;


    @ApiModelProperty("跟踪状态")
    private String trackingStatus;

    @ApiModelProperty("申请执行状态")
    private String applyTrackStatus;




    @ApiModelProperty("实际发货数量合计")
    private BigDecimal actualSendQty;


    @ApiModelProperty("实际到货数量合计")
    private BigDecimal actualArrivalQty;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;


    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;


    @ApiModelProperty("同步结果")
    private String syncResultMsg;


    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    @ApiModelProperty("物料信息(JSON)")
    private String materialInfo;



}
