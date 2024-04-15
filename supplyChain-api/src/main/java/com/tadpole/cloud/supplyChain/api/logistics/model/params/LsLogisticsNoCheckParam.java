package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 物流单对账 入参类
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
@TableName("LS_LOGISTICS_NO_CHECK")
public class LsLogisticsNoCheckParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ApiModelProperty("出货仓名称（多个用英文逗号分隔）")
    private String outWarehouseName;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 合约号（物流账号） */
    @ApiModelProperty("合约号（物流账号）")
    private String lcCode;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 是否报关：是，否 */
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    /** 是否包税：是，否 */
    @ApiModelProperty("是否包税：是，否")
    private String hasTax;

    /** 货运方式1 */
    @ApiModelProperty("货运方式1")
    private String freightCompany;

    /** 运输方式 */
    @ApiModelProperty("运输方式")
    private String transportType;

    /** 物流渠道 */
    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    /** 红蓝单 */
    @ApiModelProperty("红蓝单")
    private String orderType;

    /** 发货件数 */
    @ApiModelProperty("发货件数")
    private Long shipmentUnit;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    private Long shipmentQuantity;

    /** 出仓重量（KG） */
    @ApiModelProperty("出仓重量（KG）")
    private BigDecimal weight;

    /** 出仓体积（CBM） */
    @ApiModelProperty("出仓体积（CBM）")
    private BigDecimal volume;

    /** 出仓体积重（KG） */
    @ApiModelProperty("出仓体积重（KG）")
    private BigDecimal volumeWeight;

    /** 计费类型 */
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    /** 计费量 */
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    /** ShipmentID */
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    /** 预估单价（CNY） */
    @ApiModelProperty("预估单价（CNY）")
    private BigDecimal predictUnitPrice;

    /** 预估物流费（CNY） */
    @ApiModelProperty("预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    /** 预估燃油附加费（CNY） */
    @ApiModelProperty("预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    /** 预估旺季附加费（CNY） */
    @ApiModelProperty("预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    /** 预估附加费及杂费（CNY） */
    @ApiModelProperty("预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    /** 预估报关费（CNY） */
    @ApiModelProperty("预估报关费（CNY）")
    private BigDecimal predictCustomsFee;

    /** 预估清关费（CNY） */
    @ApiModelProperty("预估清关费（CNY）")
    private BigDecimal predictClearCustomsFee;

    /** 预估税费（CNY） */
    @ApiModelProperty("预估税费（CNY）")
    private BigDecimal predictTaxFee;

    /** 预估总费用（CNY） */
    @ApiModelProperty("预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 签收日期 */
    @ApiModelProperty("签收日期")
    private Date signDate;

    /** 签收人 */
    @ApiModelProperty("签收人")
    private String signUser;

    /** 支付次数 */
    @ApiModelProperty("支付次数")
    private Long paymentCount;

    /** 对账状态：对账中，对账完成 */
    @ApiModelProperty("对账状态：对账中，对账完成")
    private String checkStatus;

    /** 锁定状态：锁定，未锁定 */
    @ApiModelProperty("锁定状态：锁定，未锁定")
    private String lockStatus;

    /** 总费用差异（CNY） */
    @ApiModelProperty("总费用差异（CNY）")
    private BigDecimal diffTotalFee;

    /** 数据来源：EBMS，JCERP */
    @ApiModelProperty("数据来源：EBMS，JCERP")
    private String dataSource;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 发货批次号集合 */
    @ApiModelProperty("发货批次号集合")
    private List<String> shipmentNumList;

    /** 账号集合 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNameList;

    /** 站点集合 */
    @ApiModelProperty("站点集合")
    private List<String> sysSiteList;

    /** 物流单号集合 */
    @ApiModelProperty("物流单号集合")
    private List<String> logisticsNoList;

    /** 发货方式1集合 */
    @ApiModelProperty("发货方式1集合")
    private List<String> freightCompanyList;

    /** 运输方式集合 */
    @ApiModelProperty("运输方式集合")
    private List<String> transportTypeList;

    /** 物流渠道集合 */
    @ApiModelProperty("物流渠道集合")
    private List<String> logisticsChannelList;

    /** 合约号（物流账号）集合 */
    @ApiModelProperty("合约号（物流账号）集合")
    private List<String> lcCodeList;

    /** 物流商名称集合 */
    @ApiModelProperty("物流商名称集合")
    private List<String> lpNameList;

    /** ShipmentID集合 */
    @ApiModelProperty("ShipmentID集合")
    private List<String> shipmentIdList;

    /** 物流对账单号集合 */
    @ApiModelProperty("物流对账单号集合")
    private List<String> logisticsCheckOrderList;

    /** 发货开始日期 */
    @ApiModelProperty("发货开始日期")
    private String shipmentStartDate;

    /** 发货结束日期 */
    @ApiModelProperty("发货结束日期")
    private String shipmentEndDate;

    /** 签收开始日期 */
    @ApiModelProperty("签收开始日期")
    private String signStartDate;

    /** 签收结束日期 */
    @ApiModelProperty("签收结束日期")
    private String signEndDate;

    /** 物流费ERP申请日期开始时间 */
    @ApiModelProperty(value = "物流费ERP申请日期开始时间")
    private String logisticsErpStartDate;

    /** 物流费ERP申请日期结束时间 */
    @ApiModelProperty(value = "物流费ERP申请日期结束时间")
    private String logisticsErpEndDate;

    /** 物流费单据编号集合 */
    @ApiModelProperty(value = "物流费单据编号集合")
    private List<String> logisticsBillOrderList;

    /** 税费ERP申请日期开始时间 */
    @ApiModelProperty(value = "税费ERP申请日期开始时间")
    private String taxErpStartDate;

    /** 税费ERP申请日期结束时间 */
    @ApiModelProperty(value = "税费ERP申请日期结束时间")
    private String taxErpEndDate;

    /** 税费单据编号集合 */
    @ApiModelProperty(value = "税费单据编号集合")
    private List<String> taxBillOrderList;

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

    /** 预估计费类型 */
    @ApiModelProperty("预估计费类型")
    private String predictConfirmFeeType;

    /** 预估计费量 */
    @ApiModelProperty("预估计费量")
    private BigDecimal predictConfirmCountFee;

}
