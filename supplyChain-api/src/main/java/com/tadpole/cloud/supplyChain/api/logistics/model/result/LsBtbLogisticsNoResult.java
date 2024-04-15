package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
 *  BTB物流单出参类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_BTB_LOGISTICS_NO")
public class LsBtbLogisticsNoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="BTB订单号", index = 0)
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    @ExcelProperty(value ="发货批次号", index = 1)
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ExcelProperty(value ="平台", index = 2)
    @ApiModelProperty("平台")
    private String platform;

    @ExcelProperty(value ="账号", index = 3)
    @ApiModelProperty("账号")
    private String sysShopsName;

    @ExcelProperty(value ="站点", index = 4)
    @ApiModelProperty("站点")
    private String sysSite;

    @ExcelProperty(value ="发货仓", index = 5)
    @ApiModelProperty("发货仓")
    private String shipmentWarehouse;

    @ExcelProperty(value ="物流单号", index = 6)
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ExcelProperty(value ="物流账号", index = 10)
    @ApiModelProperty("物流账号")
    private String lcCode;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ExcelProperty(value ="物流商名称", index = 12)
    @ApiModelProperty("物流商名称")
    private String lpName;

    @ExcelProperty(value ="物流商简称", index = 11)
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ExcelProperty(value ="发货日期", index = 7)
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ExcelProperty(value ="签收日期", index = 8)
    @ApiModelProperty("签收日期")
    private Date signDate;

    @ApiModelProperty("签收人")
    private String signUser;

    @ExcelProperty(value ="是否报关", index = 16)
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    @ExcelProperty(value ="报关公司", index = 17)
    @ApiModelProperty("报关公司")
    private String customsCompany;

    @ExcelProperty(value ="是否递延", index = 9)
    @ApiModelProperty("是否递延：是，否")
    private String isDefer;

    @ApiModelProperty("国家分区号")
    private String lspNum;

    @ApiModelProperty("收货分区")
    private String countryAreaName;

    @ApiModelProperty("收货国家中文名称")
    private String receiveCountryNameCn;

    @ApiModelProperty("收货国家英文名称")
    private String receiveCountryNameEn;

    @ApiModelProperty("收货国家编码")
    private String receiveCountryCode;

    @ApiModelProperty("收件人")
    private String buyerName;

    @ApiModelProperty("联系电话")
    private String phone1;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("州/省/郡")
    private String province;

    @ApiModelProperty("地址1")
    private String street1;

    @ApiModelProperty("地址2")
    private String street2;

    @ApiModelProperty("邮编")
    private String postCode;

    @ExcelProperty(value ="发货方式1", index = 13)
    @ApiModelProperty("发货方式1")
    private String freightCompany;

    @ExcelProperty(value ="运输方式", index = 14)
    @ApiModelProperty("运输方式")
    private String transportType;

    @ExcelProperty(value ="物流渠道", index = 15)
    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    @ExcelProperty(value ="红蓝单", index = 19)
    @ApiModelProperty("红蓝单")
    private String orderType;

    @ExcelProperty(value ="货物特性", index = 20)
    @ApiModelProperty("货物特性")
    private String goodsProperty;

    @ExcelProperty(value ="计费类型", index = 25)
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    @ExcelProperty(value ="计费量", index = 26)
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    @ExcelProperty(value ="物流费币制", index = 27)
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    @ExcelProperty(value ="是否包税", index = 18)
    @ApiModelProperty("是否包税")
    private String hasTax;

    @ExcelProperty(value ="出仓件数", index = 21)
    @ApiModelProperty("出仓件数")
    private Long shipmentUnit;

    @ExcelProperty(value ="发货数量", index = 22)
    @ApiModelProperty("发货数量")
    private Long shipmentQuantity;

    @ExcelProperty(value ="出仓重量（KG）", index = 23)
    @ApiModelProperty("出仓重量（KG）")
    private BigDecimal weight;

    @ExcelProperty(value ="出仓体积（CBM）", index = 24)
    @ApiModelProperty("出仓体积（CBM）")
    private BigDecimal volume;

    @ApiModelProperty("出仓体积重（KG）")
    private BigDecimal volumeWeight;

    @ExcelProperty(value ="单价", index = 28)
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value ="物流费", index = 29)
    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    @ExcelProperty(value ="燃油附加费", index = 30)
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    @ExcelProperty(value ="旺季附加费", index = 31)
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    @ExcelProperty(value ="附加费及杂费", index = 32)
    @ApiModelProperty("附加费及杂费")
    private BigDecimal othersFee;

    @ExcelProperty(value ="附加费及杂费备注", index = 33)
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    @ExcelProperty(value ="报关费", index = 34)
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    @ExcelProperty(value ="清关费", index = 35)
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    @ExcelProperty(value ="税费", index = 36)
    @ApiModelProperty("税费")
    private BigDecimal taxFee;

    @ExcelProperty(value ="备注", index = 37)
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("总费用（人工维护）")
    private BigDecimal totalFee;

    @ApiModelProperty("预估单价")
    private BigDecimal predictUnitPrice;

    @ApiModelProperty("预估物流费")
    private BigDecimal predictLogisticsFee;

    @ApiModelProperty("预估燃油附加费")
    private BigDecimal predictOilFee;

    @ApiModelProperty("预估旺季附加费")
    private BigDecimal predictBusySeasonFee;

    @ApiModelProperty("预估附加费及杂费")
    private BigDecimal predictOthersFee;

    @ApiModelProperty("预估报关费")
    private BigDecimal predictCustomsFee;

    @ApiModelProperty("预估清关费")
    private BigDecimal predictClearCustomsFee;

    @ApiModelProperty("预估税费")
    private BigDecimal predictTaxFee;

    @ApiModelProperty("预估总费用")
    private BigDecimal predictTotalFee;

    /** 总费用差异 */
    @ApiModelProperty("总费用差异")
    private BigDecimal diffTotalFee;

    @ApiModelProperty("物流状态：已发货，已完结")
    private String logisticsStatus;

    @ApiModelProperty("发货类型：物流部发货，业务部发货")
    private String shipmentType;

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
