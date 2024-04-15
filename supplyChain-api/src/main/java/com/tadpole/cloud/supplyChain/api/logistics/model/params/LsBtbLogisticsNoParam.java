package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  BTB物流单入参类
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
public class LsBtbLogisticsNoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 发货仓 */
    @ApiModelProperty("发货仓")
    private String shipmentWarehouse;

    /** 物流单号 */
    @ExcelProperty(value ="物流单号*")
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    /** 物流账号 */
    @ApiModelProperty("物流账号")
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

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    /** 签收日期 */
    @ApiModelProperty("签收日期")
    private Date signDate;

    /** 签收人 */
    @ApiModelProperty("签收人")
    private String signUser;

    /** 是否报关：是，否 */
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    /** 报关公司 */
    @ApiModelProperty("报关公司")
    private String customsCompany;

    /** 是否递延：是，否 */
    @ApiModelProperty("是否递延：是，否")
    private String isDefer;

    /** 国家分区号 */
    @ApiModelProperty("国家分区号")
    private String lspNum;

    /** 收货分区 */
    @ApiModelProperty("收货分区")
    private String countryAreaName;

    /** 收货国家中文名称 */
    @ApiModelProperty("收货国家中文名称")
    private String receiveCountryNameCn;

    /** 收货国家英文名称 */
    @ApiModelProperty("收货国家英文名称")
    private String receiveCountryNameEn;

    /** 收货国家编码 */
    @ApiModelProperty("收货国家编码")
    private String receiveCountryCode;

    /** 收件人 */
    @ApiModelProperty("收件人")
    private String buyerName;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String phone1;

    /** 城市 */
    @ApiModelProperty("城市")
    private String city;

    /** 州/省/郡 */
    @ApiModelProperty("州/省/郡")
    private String province;

    /** 地址1 */
    @ApiModelProperty("地址1")
    private String street1;

    /** 地址2 */
    @ApiModelProperty("地址2")
    private String street2;

    /** 邮编 */
    @ApiModelProperty("邮编")
    private String postCode;

    /** 发货方式1 */
    @ApiModelProperty("发货方式1")
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

    /** 货物特性 */
    @ApiModelProperty("货物特性")
    private String goodsProperty;

    /** 计费类型 */
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    /** 计费量 */
    @ExcelProperty(value ="计费量")
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    /** 物流费币制 */
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 是否包税 */
    @ApiModelProperty("是否包税")
    private String hasTax;

    /** 出仓件数 */
    @ApiModelProperty("出仓件数")
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

    @ApiModelProperty("出仓体积重（KG）")
    private BigDecimal volumeWeight;

    /** 单价 */
    @ExcelProperty(value ="单价")
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    /** 燃油附加费 */
    @ExcelProperty(value ="燃油附加费")
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ExcelProperty(value ="旺季附加费")
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 附加费及杂费 */
    @ExcelProperty(value ="附加费及杂费")
    @ApiModelProperty("附加费及杂费")
    private BigDecimal othersFee;

    /** 附加费及杂费备注 */
    @ExcelProperty(value ="附加费及杂费备注")
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    /** 报关费 */
    @ExcelProperty(value ="报关费")
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ExcelProperty(value ="清关费")
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 税费 */
    @ExcelProperty(value ="税费")
    @ApiModelProperty("税费")
    private BigDecimal taxFee;

    /** 备注 */
    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    /** 总费用（人工维护） */
    @ApiModelProperty("总费用（人工维护）")
    private BigDecimal totalFee;

    /** 预估单价 */
    @ApiModelProperty("预估单价")
    private BigDecimal predictUnitPrice;

    /** 预估物流费 */
    @ApiModelProperty("预估物流费")
    private BigDecimal predictLogisticsFee;

    /** 预估燃油附加费 */
    @ApiModelProperty("预估燃油附加费")
    private BigDecimal predictOilFee;

    /** 预估旺季附加费 */
    @ApiModelProperty("预估旺季附加费")
    private BigDecimal predictBusySeasonFee;

    /** 预估附加费及杂费 */
    @ApiModelProperty("预估附加费及杂费")
    private BigDecimal predictOthersFee;

    /** 预估报关费 */
    @ApiModelProperty("预估报关费")
    private BigDecimal predictCustomsFee;

    /** 预估清关费 */
    @ApiModelProperty("预估清关费")
    private BigDecimal predictClearCustomsFee;

    /** 预估税费 */
    @ApiModelProperty("预估税费")
    private BigDecimal predictTaxFee;

    /** 预估总费用 */
    @ApiModelProperty("预估总费用")
    private BigDecimal predictTotalFee;

    /** 总费用差异 */
    @ApiModelProperty("总费用差异")
    private BigDecimal diffTotalFee;

    /** 物流状态：已发货，已完结 */
    @ApiModelProperty("物流状态：已发货，已完结")
    private String logisticsStatus;

    /** 发货类型：物流部发货，业务部发货 */
    @ApiModelProperty("发货类型：物流部发货，业务部发货")
    private String shipmentType;

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

    /** BTB订单号集合 */
    @ApiModelProperty("BTB订单号集合")
    private List<String> orderNoList;

    /** 平台集合 */
    @ApiModelProperty("平台集合")
    private List<String> platformList;

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

    /** 物流商简称集合 */
    @ApiModelProperty("物流商简称集合")
    private List<String> lpSimpleNameList;

    /** 物流商名称集合 */
    @ApiModelProperty("物流商名称集合")
    private List<String> lpNameList;

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

    /** 创建人 */
    @ApiModelProperty("创建人")
    private List<String> createUserList;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

    /** 预估计费类型 */
    @ApiModelProperty("预估计费类型")
    private String predictFeeType;

    /** 预估计费量 */
    @ApiModelProperty("预估计费量")
    private BigDecimal predictCountFee;

}
