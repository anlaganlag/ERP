package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 物流单头程信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流单头程信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsListToHeadRouteParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    private String lhrCode ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    private Date sysAddDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    private String sysPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdDatetime ;
 
    /** 待发区仓位 */
    @ApiModelProperty(value = "待发区仓位")
    private String comWaitArea ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
 
    /** 收仓库类型 */
    @ApiModelProperty(value = "收仓库类型")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    private String packStoreHouseName ;
 
    /** 出仓发货件数 */
    @ApiModelProperty(value = "出仓发货件数")
    private Integer lhrOutGoodNum ;
 
    /** 出仓发货重量 */
    @ApiModelProperty(value = "出仓发货重量")
    private BigDecimal lhrOutGoodWeight ;
 
    /** 出仓体积 */
    @ApiModelProperty(value = "出仓体积")
    private BigDecimal lhrOutGoodVolume ;
 
    /** LhrChargeWeight */
    @ApiModelProperty(value = "LhrChargeWeight")
    private BigDecimal lhrChargeWeight ;
 
    /** 头程单状态 */
    @ApiModelProperty(value = "头程单状态")
    private String lhrState ;
 
    /** 头程单状态流程说明 */
    @ApiModelProperty(value = "头程单状态流程说明")
    private String lhrStateNote ;
 
    /** 通关数据同步状态 */
    @ApiModelProperty(value = "通关数据同步状态")
    private String lhrDataSynState ;
 
    /** 是否报关 */
    @ApiModelProperty(value = "是否报关")
    private Integer logpIsEntry ;
 
    /** 出仓日期 */
    @ApiModelProperty(value = "出仓日期")
    private Date lhrOutWarehouseDate ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    private String lcCode ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    private String lpSimpleName ;

    @ApiModelProperty(value = "物流商简称List集合")
    private List<String> lpSimpleNameList ;

   /** 物流商名称 */
   @ApiModelProperty(value = "物流商名称")
   private String lpName ;

   @ApiModelProperty(value = "物流商名称List集合")
   private List<String> lpNameList ;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司")
    private String comNameCn ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    private String lhrOddNumb ;

    @ApiModelProperty(value = "头程物流单号List集合")
    private List<String> lhrOddNumbList ;

    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    private Date lhrSendGoodDate ;
 
    /** 国家分区 */
    @ApiModelProperty(value = "国家分区")
    private String lspNum ;
 
    /** 目的地三字代码 */
    @ApiModelProperty(value = "目的地三字代码")
    private String lhrDesThrCharCode ;
 
    /** 收货分区 */
    @ApiModelProperty(value = "收货分区")
    private String countryAreaName ;
 
    /** 国家/地区 */
    @ApiModelProperty(value = "国家/地区")
    private String logRecCountry ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    private String logRecCity ;
 
    /** 州/省/郡 */
    @ApiModelProperty(value = "州/省/郡")
    private String logRecState ;
 
    /** 收货地址1 */
    @ApiModelProperty(value = "收货地址1")
    private String logRecAddress1 ;
 
    /** 收货地址2 */
    @ApiModelProperty(value = "收货地址2")
    private String logRecAddress2 ;
 
    /** 收获地址3 */
    @ApiModelProperty(value = "收获地址3")
    private String logRecAddress3 ;
 
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    private String logRecZip ;
 
    /** 收件人 */
    @ApiModelProperty(value = "收件人")
    private String logRecPerson ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    private String logRecPersonTel ;
 
    /** 收件公司 */
    @ApiModelProperty(value = "收件公司")
    private String logRecCompany ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    private String logTraMode1 ;
 
    /** 头程物流单链接 */
    @ApiModelProperty(value = "头程物流单链接")
    private String logHeadRouteLink ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    private String logTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    private String logSeaTraRoute ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    private String logSeaTraConType ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    private String logRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    private Integer logpIsIncTax ;
 
    /** 预计到货日期 */
    @ApiModelProperty(value = "预计到货日期")
    private Date lhrPreArriveDate ;
 
    /** 起飞日期 */
    @ApiModelProperty(value = "起飞日期")
    private Date lhrFlightDate ;
 
    /** 到达日期 */
    @ApiModelProperty(value = "到达日期")
    private Date lhrArrivalDate ;
 
    /** 结单日期 */
    @ApiModelProperty(value = "结单日期")
    private Date lhrEndListDate ;
 
    /** 时效 */
    @ApiModelProperty(value = "时效")
    private Integer lhrLogTimeCount ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String lhrNote ;
 
    /** 仓储位置 */
    @ApiModelProperty(value = "仓储位置")
    private String lhrPosition ;
 
    /** 报关公司 */
    @ApiModelProperty(value = "报关公司")
    private String lhrCustBroker ;
 
    /** 是否递延 */
    @ApiModelProperty(value = "是否递延")
    private String logIsDeferred ;
 
    /** 确认计费类型 */
    @ApiModelProperty(value = "确认计费类型")
    private String logComfirmBillType ;
 
    /** 确认计费量 */
    @ApiModelProperty(value = "确认计费量")
    private BigDecimal logComfirmBillVolume ;
 
    /** 境外收货人 */
    @ApiModelProperty(value = "境外收货人")
    private String lhrOverseasConsignee ;
 
    /** 预估税费 */
    @ApiModelProperty(value = "预估税费")
    private BigDecimal lhrPreLogTaxFee ;
 
    /** 预估总费用(人工维护) */
    @ApiModelProperty(value = "预估总费用(人工维护)")
    private BigDecimal lhrPreLogFeeTotalManual ;
 
    /** 预估费用是否确认 */
    @ApiModelProperty(value = "预估费用是否确认")
    private String lhrPreLogFeeIsConfirm ;
 
    /** 预估使用的计费量 */
    @ApiModelProperty(value = "预估使用的计费量")
    private BigDecimal lhrLogFeeWeight ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    private String logComfirmBillCurrency ;
 
    /** 预估单价 */
    @ApiModelProperty(value = "预估单价")
    private BigDecimal lhrPreLogUnitPrice ;
 
    /** 预估燃油附加费 */
    @ApiModelProperty(value = "预估燃油附加费")
    private BigDecimal lhrPreLogFuelFee ;
 
    /** 预估旺季附加费 */
    @ApiModelProperty(value = "预估旺季附加费")
    private BigDecimal lhrPreLogBusySeasonAddFee ;
 
    /** 预估附加费及杂费 */
    @ApiModelProperty(value = "预估附加费及杂费")
    private BigDecimal lhrPreLogAddAndSundryFee ;
 
    /** 预估报关费 */
    @ApiModelProperty(value = "预估报关费")
    private BigDecimal lhrPreLogCustDlearanceFee ;
 
    /** 预估清关费 */
    @ApiModelProperty(value = "预估清关费")
    private BigDecimal lhrPreLogCustClearanceFee ;
 
    /** 预估单价类型 */
    @ApiModelProperty(value = "预估单价类型")
    private String lhrPreLogUnitPriceType ;
 
    /** 预估附加费及杂费备注 */
    @ApiModelProperty(value = "预估附加费及杂费备注")
    private String lhrPreLogAddAndSundryFeeRemark ;

    /** 装箱明细List集合 */
    @ApiModelProperty(value = "装箱明细List集合")
    private List<TbLogisticsListToHeadRouteDetParam> headerRouteDetList;

   /** 单价 */
   @ApiModelProperty(value = "单价")
   private BigDecimal lhrLogUnitPrice ;

   /** 燃油附加费 */
   @ApiModelProperty(value = "燃油附加费")
   private BigDecimal lhrLogFuelFee ;

   /** 旺季附加费 */
   @ApiModelProperty(value = "旺季附加费")
   private BigDecimal lhrLogBusySeasonAddFee ;

   /** 附加费及杂费 */
   @ApiModelProperty(value = "附加费及杂费")
   private BigDecimal lhrLogAddAndSundryFee ;

   /** 报关费 */
   @ApiModelProperty(value = "报关费")
   private BigDecimal lhrLogCustDlearanceFee ;

   /** 清关费 */
   @ApiModelProperty(value = "清关费")
   private BigDecimal lhrLogCustClearanceFee ;

   /** 税费 */
   @ApiModelProperty(value = "税费")
   private BigDecimal lhrLogTaxFee ;

   /** 物流费 */
   @ApiModelProperty(value = "物流费")
   private BigDecimal lhrLogFee ;

   /** 预估总费用 */
   @ApiModelProperty(value = "预估总费用")
   private BigDecimal lhrPreLogFeeTotalNew ;

   /** 总费用 */
   @ApiModelProperty(value = "总费用")
   private BigDecimal lhrLogFeeTotalNew ;

   /** 预估物流费 */
   @ApiModelProperty(value = "预估物流费")
   private BigDecimal lhrPreLogFee ;

   /** 物流单状态 */
   @ApiModelProperty(value = "物流单状态")
   private String lhrLogStatus ;


    /** 发货日期 */
    @ApiModelProperty(value = "发货日期--查询开始")
    private Date lhrSendGoodDateStart ;

    @ApiModelProperty(value = "发货日期--查询结束")
    private Date lhrSendGoodDateEnd ;

}