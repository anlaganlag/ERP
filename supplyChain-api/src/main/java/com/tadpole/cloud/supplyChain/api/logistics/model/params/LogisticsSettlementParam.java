package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 物流实际结算入参类
 * @date: 2022/11/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsSettlementParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    /** 发货日期开始时间 */
    @ApiModelProperty(value = "发货日期开始时间")
    private String shipmentDateStart;

    /** 发货日期结束时间 */
    @ApiModelProperty(value = "发货日期结束时间")
    private String shipmentDateEnd;

    /** 账号集合 */
    @ApiModelProperty("账号集合")
    private List<String> shopsNames;

    /** 站点集合 */
    @ApiModelProperty("站点集合")
    private List<String> sites;

    /** 合约号集合 */
    @ApiModelProperty("合约号集合")
    private List<String> contractNos;

    /** 是否报关：是/否 */
    @ApiModelProperty(value = "是否报关：是/否")
    private String isCustoms;

    /** 是否包税：是/否 */
    @ApiModelProperty(value = "是否包税：是/否")
    private String hasTax;

    /** 货运方式1集合 */
    @ApiModelProperty("货运方式1集合")
    private List<String> freightCompanys;

    /** 运输方式集合 */
    @ApiModelProperty("运输方式集合")
    private List<String> transportTypes;

    /** 物流渠道集合 */
    @ApiModelProperty("物流渠道集合")
    private List<String> logisticsChannels;

    /** 物流单类型：红单/蓝单 */
    @ApiModelProperty(value = "物流单类型：红单/蓝单")
    private String orderType;

    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    private String logisticsOrder;

    /** shipmentID */
    @ApiModelProperty(value = "shipmentID")
    private String shipmentId;

    /** 预估类型：系统计算/人工维护 */
    @ApiModelProperty(value = "预估类型：系统计算/人工维护")
    private String predictType;

    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    private String logisticsSettlementOrder;

    /** 物流费ERP申请日期开始时间 */
    @ApiModelProperty(value = "物流费ERP申请日期开始时间")
    private String logisticsErpDateStart;

    /** 物流费ERP申请日期结束时间 */
    @ApiModelProperty(value = "物流费ERP申请日期结束时间")
    private String logisticsErpDateEnd;

    /** 物流费单据编号 */
    @ApiModelProperty(value = "物流费单据编号")
    private String logisticsBillOrder;

    /** 税费ERP申请日期开始时间 */
    @ApiModelProperty(value = "税费ERP申请日期开始时间")
    private String taxErpDateStart;

    /** 税费ERP申请日期结束时间 */
    @ApiModelProperty(value = "税费ERP申请日期结束时间")
    private String taxErpDateEnd;

    /** 税费单据编号 */
    @ApiModelProperty(value = "税费单据编号")
    private String taxBillOrder;

    /** 最小差异 */
    @ApiModelProperty(value = "差异")
    @Min(0)
    @Max(50)
    private BigDecimal minDiffTotalFee;

    /** 最大差异 */
    @ApiModelProperty(value = "最大差异")
    @Min(0)
    @Max(50)
    private BigDecimal maxDiffTotalFee;

    /** 对账状态 */
    @ApiModelProperty(value = "对账状态")
    private String billStatus;

    /** 查询是否需要明细表 */
    @ApiModelProperty(hidden = true)
    private String needDetail;
}
