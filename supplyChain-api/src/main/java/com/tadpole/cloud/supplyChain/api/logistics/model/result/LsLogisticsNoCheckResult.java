package com.tadpole.cloud.supplyChain.api.logistics.model.result;

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
 * 物流单对账 出参类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_NO_CHECK")
public class LsLogisticsNoCheckResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ApiModelProperty("出货仓名称（多个用英文逗号分隔）")
    private String outWarehouseName;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("账号")
    private String sysShopsName;

    @ApiModelProperty("站点")
    private String sysSite;

    @ApiModelProperty("合约号（物流账号）")
    private String lcCode;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    @ApiModelProperty("是否包税：是，否")
    private String hasTax;

    @ApiModelProperty("货运方式1")
    private String freightCompany;

    @ApiModelProperty("运输方式")
    private String transportType;

    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    @ApiModelProperty("红蓝单")
    private String orderType;

    @ApiModelProperty("发货件数")
    private Long shipmentUnit;

    @ApiModelProperty("发货数量")
    private Long shipmentQuantity;

    @ApiModelProperty("出仓重量（KG）")
    private BigDecimal weight;

    @ApiModelProperty("出仓体积（CBM）")
    private BigDecimal volume;

    @ApiModelProperty("出仓体积重（KG）")
    private BigDecimal volumeWeight;

    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    @ApiModelProperty("预估单价（CNY）")
    private BigDecimal predictUnitPrice;

    @ApiModelProperty("预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    @ApiModelProperty("预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    @ApiModelProperty("预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    @ApiModelProperty("预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    @ApiModelProperty("预估报关费（CNY）")
    private BigDecimal predictCustomsFee;

    @ApiModelProperty("预估清关费（CNY）")
    private BigDecimal predictClearCustomsFee;

    @ApiModelProperty("预估税费（CNY）")
    private BigDecimal predictTaxFee;

    @ApiModelProperty("预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("签收日期")
    private Date signDate;

    @ApiModelProperty("签收人")
    private String signUser;

    @ApiModelProperty("支付次数")
    private Long paymentCount;

    @ApiModelProperty("对账状态：对账中，对账完成")
    private String checkStatus;

    @ApiModelProperty("锁定状态：锁定，未锁定")
    private String lockStatus;

    @ApiModelProperty("总费用差异（CNY）")
    private BigDecimal diffTotalFee;

    @ApiModelProperty("数据来源：EBMS，JCERP")
    private String dataSource;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    /** 预估计费类型 */
    @ApiModelProperty("预估计费类型")
    private String predictFeeType;

    /** 预估计费量 */
    @ApiModelProperty("预估计费量")
    private BigDecimal predictCountFee;

}
